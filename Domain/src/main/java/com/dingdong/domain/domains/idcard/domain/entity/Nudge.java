package com.dingdong.domain.domains.idcard.domain.entity;


import com.dingdong.domain.domains.AbstractTimeStamp;
import com.dingdong.domain.domains.idcard.domain.enums.NudgeType;
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
@Table(name = "tbl_nudge")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Nudge extends AbstractTimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private NudgeType type;

    @NotNull private Long communityId;

    @NotNull private Long fromUserId;

    @NotNull private Long toUserId;

    private Nudge(NudgeType type, Long communityId, Long fromUserId, Long toUserId) {
        this.type = type;
        this.communityId = communityId;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
    }

    public static Nudge toEntity(NudgeType type, Long communityId, Long fromUserId, Long toUserId) {
        return new Nudge(type, communityId, fromUserId, toUserId);
    }
}
