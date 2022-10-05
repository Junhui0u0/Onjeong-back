package com.example.onjeong.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long userId;
    private String userName;
    private String userStatus;
    private String userBirth;
    private String userNickname;
    private Long familyId;
}
