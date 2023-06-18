package com.dingdong.api.community.service.generator;


import com.dingdong.domain.domains.community.domain.strategy.GenerateCommunityInvitationCodeStrategy;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.RandomStringUtils;

public class RandomCommunityCodeGeneratorStrategy
        implements GenerateCommunityInvitationCodeStrategy {

    private static final int CODE_LENGTH = 6;

    public String generate(List<String> existingCodes) {
        Set<String> codesSet = new HashSet<>(existingCodes);
        String code;
        do {
            code = RandomStringUtils.randomAlphanumeric(CODE_LENGTH);
        } while (codesSet.contains(code));

        return code;
    }
}
