package com.dingdong.api.user.dto;


import com.dingdong.domain.domains.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileDto {
    private Long userId;
    private String email;
    private String gender;
    private String ageRange;
    private String profileImageUrl;

    public static UserProfileDto from(User user) {
        return UserProfileDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .gender(user.getGenderType().name())
                .ageRange(user.getAgeRange())
                .profileImageUrl(null)
                .build();
    }
}
