package com.dingdong.domain.domains.user.domain;


import com.dingdong.domain.domains.AbstractTimeStamp;
import com.dingdong.domain.domains.community.domain.entity.Community;
import com.dingdong.domain.domains.idcard.domain.enums.CharacterType;
import com.dingdong.domain.domains.idcard.domain.model.Character;
import com.dingdong.domain.domains.user.domain.enums.GenderType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Community> communities = new ArrayList<>();

    private User(String email, GenderType genderType, String ageRange) {
        this.email = email;
        this.genderType = genderType;
        this.ageRange = ageRange;
    }

    public static User toEntity(String email, GenderType genderType, String ageRange) {
        return new User(email, genderType, ageRange);
    }

    public void joinCommunity(Community community) {
        this.getCommunities().add(community);
    }

    public void updateCharacter(Character character) {
        this.character = character;
    }

    public CharacterType getUserCharacterType() {
        return Optional.ofNullable(character).map(Character::getCharacterType).orElse(null);
    }
}
