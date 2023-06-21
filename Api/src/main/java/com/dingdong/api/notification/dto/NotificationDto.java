package com.dingdong.api.notification.dto;


import com.dingdong.domain.domains.notification.domain.enums.NotificationStatus;
import com.dingdong.domain.domains.notification.domain.enums.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationDto {

    @Schema(description = "알림 정보")
    private NotificationType notificationType;

    @Schema(description = "알림 세부 데이터")
    private Map<String, Object> data;

    @Schema(description = "알림 상태")
    private NotificationStatus notificationStatus;

    @Schema(description = "알림 생성 시간")
    private LocalDateTime createdAt;
}
