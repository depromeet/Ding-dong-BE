package com.dingdong.domain.domains.community.domain;


import com.dingdong.domain.domains.AbstractTimeStamp;
import com.dingdong.domain.domains.idcard.domain.IdCard;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "tbl_community")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Community extends AbstractTimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 20)
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Admin> admins = new ArrayList<>();

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IdCard> idCards = new ArrayList<>();

    @Embedded private CommunityImage communityImage;

    private String invitationCode;
}
