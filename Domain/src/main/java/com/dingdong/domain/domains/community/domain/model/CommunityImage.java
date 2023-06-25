package com.dingdong.domain.domains.community.domain.model;


import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommunityImage {
    private String logoImageUrl;
    private String coverImageUrl;

    private CommunityImage(String logoImageUrl, String coverImageUrl) {
        this.logoImageUrl = logoImageUrl;
        this.coverImageUrl = coverImageUrl;
    }

    public static CommunityImage createCommunityImage(String logoImageUrl, String coverImageUrl) {
        return new CommunityImage(logoImageUrl, coverImageUrl);
    }
}
