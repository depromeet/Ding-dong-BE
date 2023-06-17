package com.dingdong.domain.domains.community.adaptor;

import static com.dingdong.domain.domains.community.exception.CommunityErrorCode.NOT_FOUND_COMMUNITY;

import com.dingdong.core.annotation.Adaptor;
import com.dingdong.core.exception.BaseException;
import com.dingdong.domain.domains.community.domain.Admin;
import com.dingdong.domain.domains.community.domain.Community;
import com.dingdong.domain.domains.community.domain.enums.AdminRole;
import com.dingdong.domain.domains.community.repository.AdminRepository;
import com.dingdong.domain.domains.community.repository.CommunityRepository;
import com.dingdong.domain.domains.user.domain.User;
import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class CommunityAdaptor {

    private final CommunityRepository communityRepository;
    private final AdminRepository adminRepository;

    public Community findById(Long communityId) {
        return communityRepository
                .findById(communityId)
                .orElseThrow(() -> new BaseException(NOT_FOUND_COMMUNITY));
    }

    // 이미 존재하는 초대 코드인지 체크
    public boolean isAlreadyExistInvitationCode(String invitationCode) {
        return communityRepository.existsCommunityByInvitationCode(invitationCode);
    }

    public Community save(Community community, User user) {
        community.addAdmin(createAndSaveAdmin(user.getId()));
        return communityRepository.save(community);
    }

    private Admin createAndSaveAdmin(Long userId) {
        return adminRepository.save(Admin.toEntity(AdminRole.ADMIN, userId));
    }
}
