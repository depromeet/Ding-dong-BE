package com.dingdong.domain.domains.user.domain.entity;

import static com.dingdong.domain.common.consts.Status.N;
import static com.dingdong.domain.common.consts.Status.Y;

import com.dingdong.domain.common.consts.Status;
import com.dingdong.domain.domains.AbstractTimeStamp;
import com.dingdong.domain.domains.idcard.domain.enums.CharacterType;
import com.dingdong.domain.domains.idcard.domain.model.Character;
import com.dingdong.domain.domains.user.domain.enums.GenderType;
import java.util.Optional;
import javax.persistence.Embedded;
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
@Table(name = "tbl_user")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AbstractTimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Enumerated(EnumType.STRING)
    private GenderType genderType;

    private String ageRange;

    @Embedded private Character character;

    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    private Status isDeleted;

    private User(String email, GenderType genderType, String ageRange) {
        this.email = email;
        this.genderType = genderType;
        this.ageRange = ageRange;
        this.isDeleted = N;
    }

    public static User toEntity(String email, GenderType genderType, String ageRange) {
        return new User(email, genderType, ageRange);
    }

    public void updateCharacter(Character character) {
        this.character = character;
    }

    public CharacterType getUserCharacterType() {
        return Optional.ofNullable(character).map(Character::getCharacterType).orElse(null);
    }

    public void updateProfileImage(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void withdraw() {
        this.isDeleted = Y;
    }
}
