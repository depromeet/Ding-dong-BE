package com.dingdong.domain.domains.idcard.domain;


import com.dingdong.domain.domains.AbstractTimeStamp;
import com.dingdong.domain.domains.idcard.domain.enums.CharacterType;
import java.util.ArrayList;
import java.util.List;
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
@Table(name = "tbl_id_card")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IdCard extends AbstractTimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded private UserInfo userInfo;

    @Enumerated(EnumType.STRING)
    private CharacterType character;

    @OneToMany(mappedBy = "idCard", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Keyword> keywords = new ArrayList<>();

    private IdCard(UserInfo userInfo, CharacterType character) {
        this.userInfo = userInfo;
        this.character = character;
    }

    public static IdCard toEntity(UserInfo userInfo, CharacterType character) {
        return new IdCard(userInfo, character);
    }
}
