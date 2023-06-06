package com.dingdong.domain.domains.idcard.domain.entity;


import com.dingdong.domain.domains.AbstractTimeStamp;
import com.dingdong.domain.domains.idcard.domain.vo.UserInfo;
import java.util.ArrayList;
import java.util.List;
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

    public void updateKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
    }
}
