package com.dingdong.api.global.event;


import com.dingdong.api.notification.service.NotificationService;
import com.dingdong.domain.domains.notification.domain.entity.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class EventListener {
    private final NotificationService notificationService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void notify(Notification notification) {
        notificationService.notify(notification.getUserId(), true);
    }
}
