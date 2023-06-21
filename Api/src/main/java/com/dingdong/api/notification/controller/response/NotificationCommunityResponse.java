package com.dingdong.api.notification.controller.response;


import com.dingdong.api.notification.dto.NotificationCommunityDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationCommunityResponse {

    @Schema(description = "알림 탭에 띄워질 행성 목록")
    List<NotificationCommunityDto> notificationCommunityDtos;
}
