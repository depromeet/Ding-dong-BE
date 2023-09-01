package com.dingdong.api.nudge.service;


import com.dingdong.api.global.helper.UserHelper;
import com.dingdong.api.nudge.controller.request.NudgeRequest;
import com.dingdong.domain.domains.community.adaptor.CommunityAdaptor;
import com.dingdong.domain.domains.community.domain.entity.Community;
import com.dingdong.domain.domains.idcard.adaptor.NudgeAdaptor;
import com.dingdong.domain.domains.idcard.domain.entity.Nudge;
import com.dingdong.domain.domains.idcard.domain.enums.NudgeType;
import com.dingdong.domain.domains.idcard.validator.IdCardValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NudgeService {

    private final NudgeAdaptor nudgeAdaptor;
    private final CommunityAdaptor communityAdaptor;
    private final IdCardValidator idCardValidator;
    private final UserHelper userHelper;

    @Transactional
    public void createNudge(Long toUserId, NudgeRequest body) {
        Community community = communityAdaptor.findById(body.getCommunityId());

        Long currentUserId = userHelper.getCurrentUserId();

        idCardValidator.validateUserIdCardInCommunity(community.getId(), currentUserId);
        idCardValidator.validateUserIdCardInCommunity(community.getId(), toUserId);

        nudgeAdaptor.save(
                Nudge.toEntity(
                        NudgeType.findNudgeType(body.getNudgeType()),
                        community.getId(),
                        currentUserId,
                        toUserId));
    }
}
