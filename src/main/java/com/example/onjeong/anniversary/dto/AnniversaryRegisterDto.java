package com.example.onjeong.anniversary.dto;

import com.example.onjeong.anniversary.domain.AnniversaryType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class AnniversaryRegisterDto {
    private String anniversaryContent;
    private String anniversaryType;
}
