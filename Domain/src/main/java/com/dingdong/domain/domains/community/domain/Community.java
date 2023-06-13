package com.dingdong.domain.domains.community.domain;


import com.dingdong.domain.domains.AbstractTimeStamp;
import com.dingdong.domain.domains.idcard.domain.entity.IdCard;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Admin> admins = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<IdCard> idCards = new ArrayList<>();

    @Embedded private CommunityImage communityImage;

    private String description;

    private String invitationCode;

    private Community(String name, CommunityImage communityImage, String invitationCode) {
        this.name = name;
        this.communityImage = communityImage;
        this.invitationCode = invitationCode;
    }

    public static Community toEntity(
            String name, CommunityImage communityImage, String invitationCode) {
        return new Community(name, communityImage, invitationCode);
    }

    public String getLogoImageUrl() {
        return Optional.ofNullable(communityImage)
                .map(CommunityImage::getLogoImageUrl)
                .orElse(null);
    }

    public String getCoverImageUrl() {
        return Optional.ofNullable(communityImage)
                .map(CommunityImage::getCoverImageUrl)
                .orElse(null);
    }
}
