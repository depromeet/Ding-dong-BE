package com.dingdong.domain.domains.community.domain.strategy;


import java.util.List;

@FunctionalInterface
public interface GenerateCommunityInvitationCodeStrategy {
    String generate(List<String> codes);
}
