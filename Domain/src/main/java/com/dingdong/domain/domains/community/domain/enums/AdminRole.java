package com.dingdong.domain.domains.community.domain.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AdminRole {
    MANAGER("MANAGER"),
    ADMIN("ADMIN");

    private String value;
}
