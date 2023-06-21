package com.dingdong.api.notification.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class NotificationCommunityDto {

    @Schema(description = "행성 Id")
    private Long communityId;

    @Schema(description = "로고 이미지")
    private String logoImageUrl;

    @Schema(description = "행성 이름")
    private String title;

    @Schema(description = "읽지 않은 알림 여부")
    private boolean containsUnreadNotifications;
}
