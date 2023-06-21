package com.dingdong.api.notification.controller;


import com.dingdong.api.notification.dto.NotificationDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Tag(name = "알림")
@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    @Operation(summary = "알림 구독")
    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe() {
        return new SseEmitter();
    }

    @Operation(summary = "알림 목록 조회")
    @GetMapping
    public NotificationDto getNotifications(
            @RequestParam(required = false, defaultValue = "null") Long communityId,
            @PageableDefault Pageable pageable) {
        return new NotificationDto();
    }
}
