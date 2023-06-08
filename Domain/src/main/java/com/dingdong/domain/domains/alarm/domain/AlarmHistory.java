package com.dingdong.domain.domains.alarm.domain;


import com.dingdong.core.consts.Status;
import com.dingdong.domain.domains.AbstractTimeStamp;
import com.dingdong.domain.domains.alarm.domain.enums.AlarmType;
import com.esotericsoftware.kryo.serializers.FieldSerializer.NotNull;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "tbl_alarm_history")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlarmHistory extends AbstractTimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    @Enumerated(EnumType.STRING)
    private Status isRead;

    @NotNull private Long toUserId;

    @NotNull private Long fromUserId;

    private String title;

    private String content;

    @NotNull private Long communityId;

    private String targetUrl;

    private AlarmHistory(
            AlarmType alarmType,
            Long toUserId,
            Long fromUserId,
            String title,
            String content,
            Long communityId,
            String targetUrl) {
        this.alarmType = alarmType;
        this.isRead = Status.N;
        this.toUserId = toUserId;
        this.fromUserId = fromUserId;
        this.title = title;
        this.content = content;
        this.communityId = communityId;
        this.targetUrl = targetUrl;
    }

    public static AlarmHistory toEntity(
            AlarmType alarmType,
            Long toUserId,
            Long fromUserId,
            String title,
            String content,
            Long communityId,
            String targetUrl) {
        return new AlarmHistory(
                alarmType, toUserId, fromUserId, title, content, communityId, targetUrl);
    }
}
