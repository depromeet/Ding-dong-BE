package com.dingdong.api.notification.controller;


import com.dingdong.api.notification.controller.response.NotificationResponse;
import com.dingdong.api.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@SecurityRequirement(name = "access-token")
@Tag(name = "알림")
@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "알림 구독")
    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe() {
        return notificationService.subscribe();
    }

    @Operation(summary = "알림 읽음 처리")
    @PutMapping("/{notificationId}/read")
    public void readNotification(@PathVariable Long notificationId) {
        notificationService.readNotification(notificationId);
    }

    @Operation(summary = "알림 목록 조회")
    @GetMapping
    public NotificationResponse getNotifications() {
        return NotificationResponse.from(notificationService.getNotifications());
    }

    @Operation(summary = "미확인 알림 존재 여부")
    @GetMapping("/unread")
    public boolean existsUnreadNotifications() {
        return notificationService.existsUnreadNotifications();
    }
}
