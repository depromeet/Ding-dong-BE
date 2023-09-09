package com.dingdong.api.nudge.service;

import static com.dingdong.domain.domains.idcard.exception.IdCardErrorCode.NOT_FOUND_ID_CARD;
import static com.dingdong.domain.domains.idcard.exception.IdCardErrorCode.NOT_FOUND_NUDGE;

import com.dingdong.api.global.helper.UserHelper;
import com.dingdong.api.nudge.controller.request.NudgeRequest;
import com.dingdong.api.nudge.dto.NudgeInfoDto;
import com.dingdong.core.exception.BaseException;
import com.dingdong.domain.common.util.SliceUtil;
import com.dingdong.domain.domains.community.adaptor.CommunityAdaptor;
import com.dingdong.domain.domains.community.domain.entity.Community;
import com.dingdong.domain.domains.idcard.adaptor.IdCardAdaptor;
import com.dingdong.domain.domains.idcard.adaptor.NudgeAdaptor;
import com.dingdong.domain.domains.idcard.domain.entity.IdCard;
import com.dingdong.domain.domains.idcard.domain.entity.Nudge;
import com.dingdong.domain.domains.idcard.domain.enums.NudgeType;
import com.dingdong.domain.domains.idcard.domain.model.NudgeVo;
import com.dingdong.domain.domains.idcard.validator.IdCardValidator;
import com.dingdong.domain.domains.idcard.validator.NudgeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NudgeService {

    private final NudgeAdaptor nudgeAdaptor;
    private final NudgeValidator nudgeValidator;
    private final CommunityAdaptor communityAdaptor;
    private final IdCardValidator idCardValidator;
    private final IdCardAdaptor idCardAdaptor;
    private final UserHelper userHelper;

    @Transactional
    public void createNudge(Long toUserId, NudgeRequest body) {
        Community community = communityAdaptor.findById(body.getCommunityId());

        Long currentUserId = userHelper.getCurrentUserId();

        idCardValidator.validateUserIdCardInCommunity(community.getId(), currentUserId);
        idCardValidator.validateUserIdCardInCommunity(community.getId(), toUserId);

        nudgeValidator.isAlreadyCreateNudge(body.getCommunityId(), currentUserId, toUserId);
        nudgeAdaptor.save(
                Nudge.toEntity(
                        NudgeType.findNudgeType(body.getNudgeType()),
                        community.getId(),
                        currentUserId,
                        getCurrentUserIdCard(community.getId(), currentUserId).getId(),
                        toUserId));
    }

    @Transactional
    public void updateNudge(Long formUserId, Long nudgeId, NudgeRequest body) {
        Community community = communityAdaptor.findById(body.getCommunityId());

        Long currentUserId = userHelper.getCurrentUserId();

        idCardValidator.validateUserIdCardInCommunity(community.getId(), currentUserId);
        idCardValidator.validateUserIdCardInCommunity(community.getId(), formUserId);

        Nudge nudge =
                nudgeAdaptor
                        .findNudge(body.getCommunityId(), formUserId, currentUserId)
                        .orElseThrow(() -> new BaseException(NOT_FOUND_NUDGE));

        nudge.updateNudgeType(NudgeType.findNudgeType(body.getNudgeType()));
    }

    public String getNudgeDetail(Long fromUserId, Long communityId) {
        Community community = communityAdaptor.findById(communityId);

        Long currentUserId = userHelper.getCurrentUserId();

        idCardValidator.validateUserIdCardInCommunity(community.getId(), currentUserId);
        idCardValidator.validateUserIdCardInCommunity(community.getId(), fromUserId);

        return findNudgeType(communityId, fromUserId, currentUserId);
    }

    public Slice<NudgeInfoDto> getNudges(Pageable pageable) {
        Long currentUserId = userHelper.getCurrentUserId();

        Slice<NudgeVo> nudgeVos = nudgeAdaptor.getNudges(currentUserId, pageable);
        return SliceUtil.createSliceWithPageable(
                nudgeVos.getContent().stream()
                        .map(
                                nudgeVo ->
                                        NudgeInfoDto.of(
                                                nudgeVo,
                                                findNudgeType(
                                                        nudgeVo.getNudge().getCommunityId(),
                                                        nudgeVo.getNudge().getToUserId(),
                                                        nudgeVo.getNudge().getFromUserId())))
                        .toList(),
                pageable);
    }

    // 내가 상대에게 보낸 / 상대가 나에게 보낸 콕찌르기 타입 조회
    private String findNudgeType(Long communityId, Long fromUserId, Long toUserId) {
        return nudgeAdaptor
                .findNudge(communityId, fromUserId, toUserId)
                .map(Nudge::getType)
                .map(NudgeType::getValue)
                .orElse(null);
    }

    private IdCard getCurrentUserIdCard(Long communityId, Long userId) {
        return idCardAdaptor
                .findByUserAndCommunity(communityId, userId)
                .orElseThrow(() -> new BaseException(NOT_FOUND_ID_CARD));
    }
}
