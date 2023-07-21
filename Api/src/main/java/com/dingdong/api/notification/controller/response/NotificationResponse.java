package com.dingdong.api.notification.controller.response;


import com.dingdong.api.notification.dto.NotificationDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationResponse {
    @Schema(description = "알림 정보")
    private final List<NotificationDto> notificationDtos;

    public static NotificationResponse from(List<NotificationDto> notificationDtos) {
        return NotificationResponse.builder().notificationDtos(notificationDtos).build();
    }
}
