package com.dingdong.domain.domains.community.adaptor;

import static com.dingdong.domain.domains.community.exception.CommunityErrorCode.NOT_FOUND_COMMUNITY;
import static com.dingdong.domain.domains.community.exception.CommunityErrorCode.NOT_JOIN_COMMUNITY;

import com.dingdong.core.annotation.Adaptor;
import com.dingdong.core.exception.BaseException;
import com.dingdong.domain.domains.community.domain.entity.Admin;
import com.dingdong.domain.domains.community.domain.entity.Community;
import com.dingdong.domain.domains.community.domain.entity.UserJoinCommunity;
import com.dingdong.domain.domains.community.domain.enums.AdminRole;
import com.dingdong.domain.domains.community.repository.AdminRepository;
import com.dingdong.domain.domains.community.repository.CommunityRepository;
import com.dingdong.domain.domains.community.repository.UserJoinCommunityRepository;
import com.dingdong.domain.domains.user.domain.entity.User;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class CommunityAdaptor {

    private final CommunityRepository communityRepository;
    private final AdminRepository adminRepository;
    private final UserJoinCommunityRepository userJoinCommunityRepository;

    public List<Community> findAll() {
        return communityRepository.findAll();
    }

    public Community findById(Long communityId) {
        return communityRepository
                .findById(communityId)
                .orElseThrow(() -> new BaseException(NOT_FOUND_COMMUNITY));
    }

    public Community save(Community community, User user) {
        community.addAdmin(createAndSaveAdmin(user.getId()));
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

    public boolean isExistCommunity(Long communityId) {
        return communityRepository.existsCommunityById(communityId);
    }

    public void userJoinCommunity(UserJoinCommunity userJoinCommunity) {
        userJoinCommunityRepository.save(userJoinCommunity);
    }

    public List<UserJoinCommunity> findByUser(User user) {
        return userJoinCommunityRepository.findAllByUserId(user.getId());
    }

    public List<Community> findByUserJoin(User user) {
        List<Long> communities =
                findByUser(user).stream().map(UserJoinCommunity::getCommunityId).toList();

        return communityRepository.findAllByIdInOrderByIdDesc(communities);
    }

    public UserJoinCommunity findByUserAndCommunity(User user, Community community) {
        return userJoinCommunityRepository
                .findByUserIdAndCommunityId(user.getId(), community.getId())
                .orElseThrow(() -> new BaseException(NOT_JOIN_COMMUNITY));
    }

    public Optional<UserJoinCommunity> findByUserAndCommunityForValidate(
            Long userId, Long communityId) {
        return userJoinCommunityRepository.findByUserIdAndCommunityId(userId, communityId);
    }

    public void deleteUserJoinCommunity(UserJoinCommunity userJoinCommunity) {
        userJoinCommunityRepository.delete(userJoinCommunity);
    }

    public long getUserCount(Long communityId) {
        return communityRepository.userCountByCommunityId(communityId);
    }
}
