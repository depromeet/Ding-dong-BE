package com.dingdong.api.community.service.generator;


import java.util.List;

@FunctionalInterface
public interface GenerateCommunityInvitationCodeStrategy {
    String generate(List<String> codes);
}
