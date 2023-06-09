package com.dingdong.domain.domains.community.domain.entity;

import static com.dingdong.core.consts.StaticVal.COMMUNITY_DEFAULT_DESCRIPTION;
import static com.dingdong.core.consts.StaticVal.COMMUNITY_DEFAULT_IMAGE;
import static com.dingdong.domain.domains.idcard.exception.IdCardErrorCode.NOT_FOUND_ID_CARD;

import com.dingdong.core.exception.BaseException;
import com.dingdong.domain.domains.AbstractTimeStamp;
import com.dingdong.domain.domains.community.domain.model.CommunityImage;
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

    private Community(
            String name, CommunityImage communityImage, String description, String invitationCode) {
        this.name = name;
        this.communityImage = communityImage;
        this.description = description;
        this.invitationCode = invitationCode;
    }

    public static Community toEntity(
            String name, CommunityImage communityImage, String description, String invitationCode) {
        return new Community(name, communityImage, description, invitationCode);
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

    public static Community createCommunity(
            String name, String logoImageUrl, String invitationCode) {
        CommunityImage communityImage =
                CommunityImage.createCommunityImage(logoImageUrl, getCommunityDefaultImage());
        return Community.toEntity(
                name, communityImage, getCommunityDefaultDescription(), invitationCode);
    }

    public void updateCommunity(String name, CommunityImage communityImage, String description) {
        this.name = name;
        this.communityImage = communityImage;
        this.description = description;
    }

    public void addAdmin(Admin admin) {
        this.getAdmins().add(admin);
    }

    public void addIdCard(IdCard idCard) {
        this.getIdCards().add(idCard);
    }

    private static String getCommunityDefaultImage() {
        return COMMUNITY_DEFAULT_IMAGE;
    }

    private static String getCommunityDefaultDescription() {
        return COMMUNITY_DEFAULT_DESCRIPTION;
    }

    public boolean isAdmin(Long userId) {
        if (this.getAdmins().isEmpty()) {
            return false;
        }

        return this.getAdmins().stream().anyMatch(admin -> admin.getUserId().equals(userId));
    }

    public void deleteIdCard(IdCard idCard) {
        if (!this.idCards.remove(idCard)) {
            throw new BaseException(NOT_FOUND_ID_CARD);
        }
    }
}
