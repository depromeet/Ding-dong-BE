package com.dingdong.domain.domains.community.adaptor;

import static com.dingdong.domain.domains.community.exception.CommunityErrorCode.NOT_FOUND_COMMUNITY;

import com.dingdong.core.annotation.Adaptor;
import com.dingdong.core.exception.BaseException;
import com.dingdong.domain.domains.community.domain.Admin;
import com.dingdong.domain.domains.community.domain.Community;
import com.dingdong.domain.domains.community.domain.CommunityImage;
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
        addAdmin(community, user);
        return communityRepository.save(community);
    }

    private void addAdmin(Community community, User user) {
        Admin admin = Admin.toEntity(AdminRole.ADMIN, user.getId());
        adminRepository.save(admin);
        community.getAdmins().add(admin);
    }

    public boolean isAdminUser(Long communityId, Long userId) {
        Community community = findById(communityId);
        return community.getAdmins().stream().anyMatch(c -> c.getUserId().equals(userId));
    }

    public void update(String name, CommunityImage communityImage, String description) {}
}
