package com.dingdong.domain.domains.idcard.domain.entity;


import com.dingdong.domain.domains.AbstractTimeStamp;
import com.dingdong.domain.domains.idcard.domain.enums.CharacterType;
import com.dingdong.domain.domains.idcard.domain.model.Character;
import com.dingdong.domain.domains.idcard.domain.model.UserInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "tbl_id_card")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IdCard extends AbstractTimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull private Long communityId;

    @Embedded private UserInfo userInfo;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Keyword> keywords = new ArrayList<>();

    private IdCard(Long communityId, UserInfo userInfo) {
        this.communityId = communityId;
        this.userInfo = userInfo;
    }

    public static IdCard toEntity(Long communityId, UserInfo userInfo) {
        return new IdCard(communityId, userInfo);
    }

    public static IdCard createIdCard(
            Long communityId,
            Long userId,
            String profileImageUrl,
            String nickname,
            String aboutMe,
            Character character) {
        UserInfo userInfo = UserInfo.create(userId, profileImageUrl, nickname, aboutMe, character);
        return new IdCard(communityId, userInfo);
    }

    public IdCard updateIdCard(
            String profileImageUrl, String nickname, String aboutMe, List<Keyword> keywords) {
        this.userInfo.updateUserInfo(profileImageUrl, nickname, aboutMe);
        updateKeywords(keywords);
        return this;
    }

    public void updateKeywords(List<Keyword> keywords) {
        this.keywords.addAll(keywords);
    }

    public String getNickname() {
        return Optional.ofNullable(userInfo).map(UserInfo::getNickname).orElse(null);
    }

    public String getProfileImageUrl() {
        return Optional.ofNullable(userInfo).map(UserInfo::getProfileImageUrl).orElse(null);
    }

    public String getAboutMe() {
        return Optional.ofNullable(userInfo).map(UserInfo::getAboutMe).orElse(null);
    }

    public CharacterType getCharacterType() {
        return Optional.ofNullable(userInfo)
                .map(u -> u.getCharacter().getCharacterType())
                .orElse(null);
    }
}
