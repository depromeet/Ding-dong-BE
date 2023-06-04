package com.dingdong.domain.domains.user.domain;


import com.dingdong.domain.domains.AbstractTimeStamp;
import com.dingdong.domain.domains.user.domain.enums.GenderType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
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

    @Column(unique = true)
    @Size(max = 10)
    private String nickname;

    private String profileImageUrl;

    private User(
            String email,
            GenderType genderType,
            String ageRange,
            String nickname,
            String profileImageUrl) {
        this.email = email;
        this.genderType = genderType;
        this.ageRange = ageRange;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

    public static User toEntity(
            String email,
            GenderType genderType,
            String ageRange,
            String nickname,
            String profileImageUrl) {
        return new User(email, genderType, ageRange, nickname, profileImageUrl);
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
}
