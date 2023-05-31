package com.dingdong.domain.domains.community.domain;


import javax.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class CommunityImage {
    private String profileImageUrl;
    private String coverImageUrl;
}
