package com.dingdong.api.community.service.generator;


import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;

public class RandomCommunityCodeGeneratorStrategy
        implements GenerateCommunityInvitationCodeStrategy {

    private static final int MAX_RETRY = 10;
    private static final int CODE_LENGTH = 6;

    public String generate(List<String> codes) {
        String code = "";
        for (int i = 0; i < MAX_RETRY; i++) {
            code = RandomStringUtils.randomAlphanumeric(CODE_LENGTH);
            if (!codes.contains(code)) {
                break;
            }
        }
        return code;
    }
}
