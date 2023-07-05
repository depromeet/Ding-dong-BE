package com.dingdong.domain.domains.community.adaptor;

import static com.dingdong.domain.domains.community.exception.CommunityErrorCode.NOT_FOUND_COMMUNITY;

import com.dingdong.core.annotation.Adaptor;
import com.dingdong.core.exception.BaseException;
import com.dingdong.domain.domains.community.domain.entity.Admin;
import com.dingdong.domain.domains.community.domain.entity.Community;
import com.dingdong.domain.domains.community.domain.enums.AdminRole;
import com.dingdong.domain.domains.community.repository.AdminRepository;
import com.dingdong.domain.domains.community.repository.CommunityRepository;
import com.dingdong.domain.domains.user.domain.User;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class CommunityAdaptor {

    private final CommunityRepository communityRepository;
    private final AdminRepository adminRepository;

    public List<Community> findAll() {
        return communityRepository.findAll();
    }

    public Community findById(Long communityId) {
        return communityRepository
                .findById(communityId)
                .orElseThrow(() -> new BaseException(NOT_FOUND_COMMUNITY));
    }

    public boolean isAlreadyExistInvitationCode(String invitationCode) {
        return communityRepository.existsCommunityByInvitationCode(invitationCode);
    }

    public Community save(Community community, User user) {
        community.addAdmin(createAndSaveAdmin(user.getId()));
        user.joinCommunity(community);
        return communityRepository.save(community);
    }

    private Admin createAndSaveAdmin(Long userId) {
        return adminRepository.save(Admin.toEntity(AdminRole.ADMIN, userId));
    }

    public boolean isAlreadyExistCommunityName(String name) {
        return communityRepository.existsCommunityByName(name);
    }

    public Community findByInvitationCode(String code) {
        return communityRepository
                .findByInvitationCode(code)
                .orElseThrow(() -> new BaseException(NOT_FOUND_COMMUNITY));
    }

    public boolean existsAdminByUserId(Long userId) {
        return adminRepository.existsByUserId(userId);
    }

    public long getUserCount(Long communityId) {
        return communityRepository.userCountByCommunityId(communityId);
    }
}
