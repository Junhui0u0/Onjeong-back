package com.example.onjeong.profile.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SelfIntroductionType {
    EXPRESSION("EXPRESSION"),
    FAVORITE("FAVORITE"),
    HATE("HATE"),
    INTEREST("INTEREST");

    private final String value;
}