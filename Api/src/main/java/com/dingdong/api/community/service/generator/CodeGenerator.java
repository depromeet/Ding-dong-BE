package com.dingdong.api.community.service.generator;

public interface CodeGenerator {
    String generate();

    String createCommunityInvitationCode(int MAX_RETRY);
}
