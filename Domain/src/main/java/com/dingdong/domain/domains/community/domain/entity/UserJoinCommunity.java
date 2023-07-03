package com.dingdong.domain.domains.community.domain.entity;


import com.dingdong.domain.domains.AbstractTimeStamp;
import com.esotericsoftware.kryo.serializers.FieldSerializer.NotNull;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "tbl_user_join_community")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserJoinCommunity extends AbstractTimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull private Long userId;

    @NotNull private Long communityId;

    private UserJoinCommunity(Long userId, Long communityId) {
        this.userId = userId;
        this.communityId = communityId;
    }

    public static UserJoinCommunity toEntity(Long userId, Long communityId) {
        return new UserJoinCommunity(userId, communityId);
    }
}
