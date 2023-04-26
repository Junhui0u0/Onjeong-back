package com.example.onjeong.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class UserJoinedDto {
    private String userName;
    private String userNickname;
    private String userPassword;
    private String userStatus;
    private LocalDate userBirth;
    private String joinedNickname;
    private String userEmail;
    private Boolean checkNotification;
}
