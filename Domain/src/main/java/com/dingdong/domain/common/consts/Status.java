package com.dingdong.domain.common.consts;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    Y("true"),
    N("false");

    final String value;
}
