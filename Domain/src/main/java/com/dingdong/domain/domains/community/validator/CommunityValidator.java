package com.dingdong.domain.domains.community.validator;

import static com.dingdong.domain.domains.community.exception.CommunityErrorCode.MAX_LIMIT_COMMUNITY_NAME;
import static com.dingdong.domain.domains.community.exception.CommunityErrorCode.MIN_LIMIT_COMMUNITY_NAME;

import com.dingdong.core.annotation.Validator;
import com.dingdong.core.exception.BaseException;
import com.dingdong.domain.domains.community.adaptor.CommunityAdaptor;
import com.dingdong.domain.domains.community.exception.CommunityErrorCode;
import lombok.RequiredArgsConstructor;

@Validator
@RequiredArgsConstructor
public class CommunityValidator {

    private final CommunityAdaptor communityAdaptor;

    public void verifyAdminUser(Long communityId, Long userId) {
        if (!isAdminUser(communityId, userId)) {
            throw new BaseException(CommunityErrorCode.NO_AUTHORITY_UPDATE_COMMUNITY);
        }
    }

    private boolean isAdminUser(Long communityId, Long userId) {
        return communityAdaptor.findById(communityId).getAdmins().stream()
                .anyMatch(c -> c.getUserId().equals(userId));
    }

    public boolean isAlreadyExistInvitationCode(String code) {
        return communityAdaptor.isAlreadyExistInvitationCode(code);
    }

    public void validateDuplicatedCommunityName(String name) {
        if (communityAdaptor.isAlreadyExistCommunityName(name)) {
            throw new BaseException(CommunityErrorCode.ALREADY_EXIST_COMMUNITY_NAME);
        }
    }

    public void validateCommunityNameSize(String name) {
        if (name.length() < 1) throw new BaseException(MIN_LIMIT_COMMUNITY_NAME);
        if (name.length() > 16) throw new BaseException(MAX_LIMIT_COMMUNITY_NAME);
    }
}
