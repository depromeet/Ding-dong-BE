package com.dingdong.domain.domains.community.validator;

import static com.dingdong.domain.domains.community.exception.CommunityErrorCode.*;

import com.dingdong.core.annotation.Validator;
import com.dingdong.core.exception.BaseException;
import com.dingdong.domain.domains.community.adaptor.CommunityAdaptor;
import com.dingdong.domain.domains.community.domain.entity.Community;
import com.dingdong.domain.domains.community.exception.CommunityErrorCode;
import com.dingdong.domain.domains.user.domain.entity.User;
import java.util.List;
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

    public void validateDuplicatedCommunityName(String name) {
        if (communityAdaptor.isAlreadyExistCommunityName(name)) {
            throw new BaseException(CommunityErrorCode.ALREADY_EXIST_COMMUNITY_NAME);
        }
    }

    public void validateCommunityNameSize(String name) {
        if (name.length() < 1) throw new BaseException(MIN_LIMIT_COMMUNITY_NAME);
        if (name.length() > 16) throw new BaseException(MAX_LIMIT_COMMUNITY_NAME);
    }

    public void isAlreadyJoinCommunity(User user, Long communityId) {
        List<Community> userJoinCommunities = communityAdaptor.findByUserJoin(user);
        if (userJoinCommunities.stream().anyMatch(c -> c.getId().equals(communityId))) {
            throw new BaseException(ALREADY_JOIN_COMMUNITY);
        }
    }

    public void isExistInCommunity(User user, Long communityId) {
        List<Community> userJoinCommunities = communityAdaptor.findByUserJoin(user);
        if (userJoinCommunities.stream().noneMatch(c -> c.getId().equals(communityId))) {
            throw new BaseException(NOT_JOIN_COMMUNITY);
        }
    }

    public void isExistCommunity(Long communityId) {
        if (!communityAdaptor.isExistCommunity(communityId)) {
            throw new BaseException(NOT_FOUND_COMMUNITY);
        }
    }
}
