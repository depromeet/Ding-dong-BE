package com.dingdong.api.user.dto;


import com.dingdong.domain.domains.community.domain.entity.Community;
import com.dingdong.domain.domains.idcard.domain.enums.CharacterType;
import com.dingdong.domain.domains.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileDto {
    @Schema(description = "사용자 ID")
    private Long userId;

    @Schema(description = "사용자 이메일")
    private String email;

    @Schema(description = "사용자 성별")
    private String gender;

    @Schema(description = "사용자 나이대")
    private String ageRange;

    @Schema(description = "사용자 프로필 이미지 Url")
    private String profileImageUrl;

    @Schema(description = "사용자 캐릭터 타입 (캐릭터 이름 : 생성한 캐릭터 타입, null : 캐릭터를 생성하지 않은 경우)")
    private CharacterType characterType;

    @Schema(description = "사용자가 속한 행성 ID 리스트")
    private List<Long> communityIds;

    public static UserProfileDto from(User user) {
        return UserProfileDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .gender(user.getGenderType().name())
                .ageRange(user.getAgeRange())
                .profileImageUrl(null)
                .characterType(user.getUserCharacterType())
                .communityIds(user.getCommunities().stream().map(Community::getId).toList())
                .build();
    }
}
