package com.dingdong.api.nudge.service;

import static com.dingdong.domain.domains.idcard.exception.IdCardErrorCode.NOT_FOUND_ID_CARD;
import static com.dingdong.domain.domains.idcard.exception.IdCardErrorCode.NOT_FOUND_NUDGE;

import com.dingdong.api.global.helper.UserHelper;
import com.dingdong.api.nudge.controller.request.NudgeRequest;
import com.dingdong.api.nudge.dto.NudgeInfoDto;
import com.dingdong.core.exception.BaseException;
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
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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

        IdCard fromIdCardId =
                idCardAdaptor.findByCommunityIdAndUserId(body.getCommunityId(), currentUserId);

        IdCard toIdCardId = idCardAdaptor.findByCommunityIdAndUserId(community.getId(), toUserId);

        nudgeValidator.isAlreadyCreateNudge(body.getCommunityId(), currentUserId, toUserId);
        nudgeAdaptor.save(
                Nudge.toEntity(
                        NudgeType.findNudgeType(body.getNudgeType()),
                        fromIdCardId.getId(),
                        toIdCardId.getId()));
    }

    @Transactional
    public void updateNudge(Long toUserId, NudgeRequest body) {
        Community community = communityAdaptor.findById(body.getCommunityId());

        Long currentUserId = userHelper.getCurrentUserId();

        IdCard fromIdCardId =
                idCardAdaptor.findByCommunityIdAndUserId(body.getCommunityId(), currentUserId);

        IdCard toIdCardId = idCardAdaptor.findByCommunityIdAndUserId(community.getId(), toUserId);

        Nudge nudge =
                nudgeAdaptor
                        .findNudge(fromIdCardId.getId(), toIdCardId.getId())
                        .orElseThrow(() -> new BaseException(NOT_FOUND_NUDGE));

        nudge.updateNudgeType(NudgeType.findNudgeType(body.getNudgeType()));
    }

    public String getNudgeDetail(Long userId, Long communityId) {
        Community community = communityAdaptor.findById(communityId);

        Long currentUserId = userHelper.getCurrentUserId();

        IdCard fromIdCardId = idCardAdaptor.findByCommunityIdAndUserId(community.getId(), userId);

        IdCard toIdCardId =
                idCardAdaptor.findByCommunityIdAndUserId(community.getId(), currentUserId);

        return findNudgeType(fromIdCardId.getId(), toIdCardId.getId());
    }

    public List<NudgeInfoDto> getNudges(Pageable pageable) {
        Long currentUserId = userHelper.getCurrentUserId();

        List<NudgeVo> nudgeVos = nudgeAdaptor.getNudges(currentUserId, pageable);
        return Stream.of(nudgeVos)
                .flatMap(List::stream)
                .map(
                        nudgeVo ->
                                NudgeInfoDto.of(
                                        nudgeVo,
                                        findNudgeType(
                                                nudgeVo.getNudge().getFromUserIdCardId(),
                                                nudgeVo.getNudge().getToUserIdCardId())))
                .toList();
    }

    // 내가 상대에게 보낸 / 상대가 나에게 보낸 콕찌르기 타입 조회
    private String findNudgeType(Long fromUserId, Long toUserId) {
        return nudgeAdaptor
                .findNudge(fromUserId, toUserId)
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
