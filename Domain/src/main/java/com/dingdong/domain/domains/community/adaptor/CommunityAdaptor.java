package com.dingdong.domain.domains.community.adaptor;

import static com.dingdong.domain.domains.community.exception.CommunityErrorCode.NOT_FOUND_COMMUNITY;

import com.dingdong.core.annotation.Adaptor;
import com.dingdong.core.exception.BaseException;
import com.dingdong.domain.domains.community.domain.Community;
import com.dingdong.domain.domains.community.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class CommunityAdaptor {

    private final CommunityRepository communityRepository;

    public Community find(Long communityId) {
        return communityRepository
                .findById(communityId)
                .orElseThrow(() -> new BaseException(NOT_FOUND_COMMUNITY));
    }
}
