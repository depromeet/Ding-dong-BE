package com.dingdong.domain.domains.community.validator;


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
        if (!(isAdminUser(communityId, userId))) {
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
}
