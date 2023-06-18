package com.dingdong.api.community.service.generator;


import com.dingdong.domain.domains.community.adaptor.CommunityAdaptor;
import org.apache.commons.lang3.RandomStringUtils;

public class RandomCodeGenerator implements CodeGenerator {

    private final CommunityAdaptor communityAdaptor;
    private static final int CODE_LENGTH = 6;

    public RandomCodeGenerator(CommunityAdaptor communityAdaptor) {
        this.communityAdaptor = communityAdaptor;
    }

    @Override
    public String generate() {
        return RandomStringUtils.randomAlphanumeric(CODE_LENGTH);
    }

    @Override
    public String createCommunityInvitationCode(int MAX_RETRY) {
        String code = "";
        for (int i = 0; i < MAX_RETRY; i++) {
            code = generate();
            if (!communityAdaptor.isAlreadyExistInvitationCode(code)) {
                break;
            }
        }
        return code;
    }
}
