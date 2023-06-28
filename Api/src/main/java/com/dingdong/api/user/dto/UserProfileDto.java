package com.dingdong.api.user.dto;


import com.dingdong.domain.domains.community.domain.entity.Community;
import com.dingdong.domain.domains.idcard.domain.enums.CharacterType;
import com.dingdong.domain.domains.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.stream.Collectors;
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

    @Schema(description = "사용자 캐릭터 타입")
    private CharacterType characterType;

    @Schema(description = "사용자 캐릭터 생성 여부 (true : 캐릭터 타입 존재, false : 캐릭터 타입 null)")
    private boolean isCharacterCreated;

    @Schema(description = "사용자가 속한 행성 ID 리스트")
    private List<Long> planetIds;

    public static UserProfileDto from(User user, boolean isCharacterCreated) {
        return UserProfileDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .gender(user.getGenderType().name())
                .ageRange(user.getAgeRange())
                .profileImageUrl(null)
                .characterType(user.getUserCharacterType())
                .isCharacterCreated(isCharacterCreated)
                .planetIds(
                        user.getCommunities().stream()
                                .map(Community::getId)
                                .collect(Collectors.toList()))
                .build();
    }
}
