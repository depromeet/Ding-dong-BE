package com.dingdong.api.notification.service;


import com.dingdong.api.global.helper.UserHelper;
import com.dingdong.api.notification.dto.NotificationDto;
import com.dingdong.domain.domains.notification.adaptor.NotificationAdaptor;
import com.dingdong.domain.domains.notification.domain.entity.Notification;
import com.dingdong.domain.domains.notification.domain.enums.NotificationStatus;
import com.dingdong.domain.domains.notification.domain.enums.NotificationType;
import com.dingdong.domain.domains.notification.domain.model.NotificationContent;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {
    // 기본 타임아웃 설정
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    private final EmitterRepository emitterRepository;
    private final UserHelper userHelper;
    private final NotificationAdaptor notificationAdaptor;
    private final ApplicationEventPublisher applicationEventPublisher;

    /** 클라이언트가 구독을 위해 호출하는 메서드. */
    public SseEmitter subscribe() {
        Long userId = userHelper.getCurrentUserId();
        SseEmitter emitter = createAndSaveEmitter(userId);

        sendNotification(userId, "EventStream Created. [userId=" + userId + "]");
        return emitter;
    }

    @Async("threadPoolTaskExecutor")
    public void notify(Long userId, Object event) {
        sendNotification(userId, event);
    }

    /**
     * 클라이언트에게 데이터를 전송
     *
     * @param id - 데이터를 받을 사용자의 아이디.
     * @param data - 전송할 데이터.
     */
    private void sendNotification(Long id, Object data) {
        SseEmitter emitter = emitterRepository.get(id);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().id(String.valueOf(id)).name("sse").data(data));
            } catch (IOException exception) {
                emitterRepository.deleteById(id);
                emitter.completeWithError(exception);
            }
        }
    }

    /**
     * 사용자 아이디를 기반으로 이벤트 Emitter를 생성
     *
     * @param id - 사용자 아이디.
     * @return SseEmitter - 생성된 이벤트 Emitter.
     */
    private SseEmitter createAndSaveEmitter(Long id) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(id, emitter);

        // Emitter가 완료될 때(모든 데이터가 성공적으로 전송된 상태) Emitter를 삭제한다.
        emitter.onCompletion(() -> emitterRepository.deleteById(id));
        // Emitter가 타임아웃 되었을 때(지정된 시간동안 어떠한 이벤트도 전송되지 않았을 때) Emitter를 삭제한다.
        emitter.onTimeout(() -> emitterRepository.deleteById(id));

        return emitter;
    }

    public List<NotificationDto> getNotifications() {
        Long userId = userHelper.getCurrentUserId();

        return Stream.of(
                        notificationAdaptor.getCommentsNotification(userId),
                        notificationAdaptor.getCommentRepliesNotification(userId),
                        notificationAdaptor.getCommentLikesNotification(userId),
                        notificationAdaptor.getCommentReplyLikesNotification(userId))
                .flatMap(List::stream)
                .map(NotificationDto::from)
                .sorted(Comparator.comparing(NotificationDto::getCreatedAt).reversed())
                .toList();
    }

    @Transactional
    public void readNotification(Long notificationId) {
        Notification notification = notificationAdaptor.findById(notificationId);
        Long userId = userHelper.getCurrentUserId();

        notification.read(userId);
    }

    public boolean existsUnreadNotifications() {
        List<NotificationDto> notificationDtos = getNotifications();

        return notificationDtos.stream()
                .anyMatch(
                        notificationDto ->
                                notificationDto
                                        .getNotificationStatus()
                                        .equals(NotificationStatus.UNREAD));
    }

    @Transactional
    public void createAndPublishNotification(
            Long toUserId,
            Long fromUserIdCardId,
            NotificationType type,
            NotificationContent content) {
        if (Objects.equals(userHelper.getCurrentUser().getId(), toUserId)) {
            return;
        }
        Notification notification = Notification.create(toUserId, fromUserIdCardId, type, content);
        notificationAdaptor.save(notification);
        applicationEventPublisher.publishEvent(notification);
    }
}
