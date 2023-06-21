package com.dingdong.api.notification.dto;


import com.dingdong.domain.domains.notification.domain.enums.NotificationStatus;
import com.dingdong.domain.domains.notification.domain.enums.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationDto {

    @Schema(description = "알림 종류")
    private NotificationType notificationType;

    @Schema(description = "알림 상태")
    private NotificationStatus notificationStatus;

    @Schema(description = "알림 생성 시간")
    private LocalDateTime createdAt;

    @Schema(description = "커뮤니티 정보")
    private CommunityDto communityDto;

    @Schema(description = "상대방 정보")
    private UserDto userDto;

    @Schema(description = "댓글 or 답글 정보")
    private CommentDto commentDto;

    @Schema(description = "주민증 정보")
    private IdCardDto idCardDto;
}
