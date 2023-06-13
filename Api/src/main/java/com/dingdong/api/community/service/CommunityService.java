package com.dingdong.api.community.service;


import com.dingdong.api.community.dto.CommunityDetailsDto;
import com.dingdong.domain.domains.community.adaptor.CommunityAdaptor;
import com.dingdong.domain.domains.community.domain.Community;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityAdaptor communityAdaptor;

    public CommunityDetailsDto getCommunityDetails(Long communityId) {
        Community community = communityAdaptor.findById(communityId);

        return CommunityDetailsDto.of(community, community.getIdCards().size());
    }
}
