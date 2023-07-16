package com.dingdong.api.global.event;


import com.dingdong.api.notification.service.NotificationService;
import com.dingdong.domain.domains.image.domain.entity.DeleteImage;
import com.dingdong.domain.domains.notification.domain.entity.Notification;
import com.dingdong.infrastructure.image.ImageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class EventListener {
    private final NotificationService notificationService;
    private final ImageHandler imageHandler;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void notifyAfterCommit(Notification notification) {
        notificationService.notify(notification.getToUserId(), true);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void deleteS3ImageAfterCommit(DeleteImage deleteImage) {
        imageHandler.removeImage(deleteImage.getImageUrl());
    }
}
