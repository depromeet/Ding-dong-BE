package com.dingdong.domain.domains.notification.repository;


import com.dingdong.domain.domains.notification.domain.vo.NotificationVO;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface NotificationRepositoryExtension {
    Slice<NotificationVO> findNotificationByConditionInPage(Long userId, Pageable pageable);

    List<NotificationVO> findNotificationByCondition(Long userId);
}
