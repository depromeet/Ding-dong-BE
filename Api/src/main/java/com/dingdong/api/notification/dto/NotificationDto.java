package com.dingdong.api.notification.dto;


import com.dingdong.domain.domains.notification.domain.enums.NotificationStatus;
import com.dingdong.domain.domains.notification.domain.enums.NotificationType;
import com.dingdong.domain.domains.notification.domain.vo.NotificationVO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.sql.Timestamp;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationDto {

    @Schema(description = "알림 고유값")
    private Long notificationId;

    @Schema(description = "알림 종류")
    private NotificationType notificationType;

    @Schema(description = "알림 상태")
    private NotificationStatus notificationStatus;

    @Schema(description = "알림 생성 시간")
    private Timestamp createdAt;

    @Schema(description = "커뮤니티 정보")
    private CommunityDto communityDto;

    @Schema(description = "상대방 정보")
    private UserDto userDto;

    @Schema(description = "댓글 or 답글 정보")
    private CommentDto commentDto;

    @Schema(description = "주민증 정보")
    private IdCardDto idCardDto;

    public static NotificationDto from(NotificationVO vo) {
        return NotificationDto.builder()
                .notificationId(vo.getNotificationId())
                .notificationType(vo.getNotificationType())
                .notificationStatus(vo.getNotificationStatus())
                .createdAt(vo.getCreatedAt())
                .communityDto(new CommunityDto(vo.getCommunityId(), vo.getCommunityName()))
                .userDto(
                        new UserDto(
                                vo.getFromIdCardId(),
                                vo.getFromUserProfileImageUrl(),
                                vo.getFromUserNickname()))
                .commentDto(new CommentDto(vo.getCommentId(), vo.getComment()))
                .idCardDto(new IdCardDto(vo.getIdCardId()))
                .build();
    }
}
