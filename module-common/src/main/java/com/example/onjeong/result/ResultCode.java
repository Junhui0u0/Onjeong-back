package com.example.onjeong.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
    // User
    SIGNUP_SUCCESS(201, "U001", "회원가입에 성공했습니다."),
    LOGIN_SUCCESS(200, "U002", "로그인에 성공했습니다."),
    LOGOUT_SUCCESS(200,"U003","로그아웃에 성공했습니다."),
    PUT_USER_INFORMATION_SUCCESS(200,"U004","회원정보를 수정했습니다."),
    DELETE_USER_SUCCESS(200,"U005","회원탈퇴에 성공했습니다."),
    GET_USER_SUCCESS(200,"U006","유저 기본정보를 조회했습니다."),
    POST_ACCESS_TOKEN_SUCCESS(200,"U007","새로운 Access Token을 발급했습니다."),
    GET_HOME_SUCCESS(200,"U008","home을 조회했습니다."),
    CHECK_ID_SUCCESS(200,"U009","id 체크에 성공했습니다."),

    // Anniversary
    GET_MONTH_ANNIVERSARY_SUCCESS(200, "A001", "월별 모든 특수일정을 조회했습니다."),
    GET_DAY_ANNIVERSARY_SUCCESS(200, "A002", "해당 일의 특수일정을 조회했습니다."),
    POST_ANNIVERSARY_SUCCESS(201, "A003", "해당 일의 특수일정을 등록했습니다."),
    DELETE_ANNIVERSARY_SUCCESS(200, "A004", "해당 일의 특수일정을 삭제했습니다."),

    // Board
    GET_ALL_BOARD_SUCCESS(200, "B001", "모든 오늘의 기록을 조회했습니다."),
    GET_BOARD_SUCCESS(200, "B002", "오늘의 기록을 조회했습니다."),
    POST_BOARD_SUCCESS(201, "B003", "오늘의 기록을 등록했습니다."),
    PUT_BOARD_SUCCESS(200, "B004", "오늘의 기록을 수정했습니다."),
    DELETE_BOARD_SUCCESS(200, "B005", "오늘의 기록을 삭제했습니다."),

    // Profile
    GET_FAMILY_SUCCESS(200,"P001","모든 가족 구성원을 조회했습니다."),
    GET_PROFILE_USER_INFORMATION_SUCCESS(200,"P002","프로필 상단에 보여줄 개인정보를 조회했습니다."),
    POST_PROFILE_IMAGE_SUCCESS(200,"P003","프로필 사진을 등록했습니다."),
    DELETE_PROFILE_IMAGE_SUCCESS(200,"P004","프로필 사진을 삭제했습니다."),
    GET_PROFILE_MESSAGE_SUCCESS(200,"P005","상태메시지를 조회했습니다."),
    POST_PROFILE_MESSAGE_SUCCESS(200,"P006","상태메시지를 등록했습니다."),
    PUT_PROFILE_MESSAGE_SUCCESS(200,"P007","상태메시지를 수정했습니다."),
    GET_FAVORITES_SUCCESS(200,"P008","좋아하는 것 목록을 조회했습니다."),
    POST_FAVORITE_SUCCESS(201,"P009","좋아하는 것을 등록했습니다."),
    DELETE_FAVORITE_SUCCESS(200,"P010","좋아하는 것을 삭제했습니다."),
    GET_HATES_SUCCESS(200,"P011","싫어하는 것 목록을 조회했습니다."),
    POST_HATE_SUCCESS(201,"P012","싫어하는 것을 등록했습니다."),
    DELETE_HATE_SUCCESS(200,"P013","싫어하는 것을 삭제했습니다."),
    GET_EXPRESSIONS_SUCCESS(200,"P014","한단어로 표현하는 것 목록을 조회했습니다."),
    POST_EXPRESSION_SUCCESS(201,"P015","한단어로 표현하는 것을 등록했습니다."),
    DELETE_EXPRESSION_SUCCESS(200,"P016","한단어로 표현하는 것을 삭제했습니다."),
    GET_INTERESTS_SUCCESS(200,"P017","관심사 목록을 조회했습니다."),
    POST_INTEREST_SUCCESS(201,"P018","관심사를 등록했습니다."),
    DELETE_INTEREST_SUCCESS(200,"P019","관심사를 삭제했습니다."),
    GET_PROFILE_SUCCESS(200,"P020","유저 프로필(개인정보+상태메시지)을 조회했습니다."),
    GET_SELF_INTRODUCTION_SUCCESS(200,"P021","유저 자기소개(좋아하는것, 싫어하는것..등)를 조회했습니다."),

    // FCM
    POST_TOKEN_SUCCESS(200, "F001", "디바이스 토큰을 등록했습니다."),
    DELETE_TOKEN_SUCCESS(200, "F002", "디바이스 토큰을 삭제했습니다."),
    GET_ALARM_SUCCESS(200, "F003", "최근 3일의 알람을 모두 조회했습니다."),
    CHECK_TOKEN_SUCCESS(200, "F004", "알림 허용 여부를 조회했습니다."),
    UPDATE_TOKEN_SUCCESS(200, "F005", "알림 허용 여부를 변경했습니다."),

    // Home
    GET_FLOWER_SUCCESS(200, "H001", "패밀리 레벨에 따른 현재 꽃 종류를 조회했습니다."),
    GET_BLOOM_SUCCESS(200, "H002", "만개한 꽃들의 목록을 조회했습니다."),
    GET_HISTORY_SUCCESS(200, "H003", "패밀리 코인 적립내역을 조회했습니다."),
    GET_COIN_SUCCESS(200, "H004", "패밀리 코인 수를 조회했습니다."),
    POST_COIN_SUCCESS(201, "H005", "랜덤 코인을 적립했습니다."),

    // Mail
    POST_MAIL_SUCCESS(201, "M001", "메일을 전송했습니다."),
    GET_RECEIVE_MAIL_SUCCESS(200, "M002", "받은 메일함을 조회했습니다."),
    GET_SEND_MAIL_SUCCESS(200, "M003", "보낸 메일함을 조회했습니다."),
    GET_ONE_MAIL_SUCCESS(200, "M004", "선택한 메일을 조회했습니다."),
    DELETE_SEND_MAIL_SUCCESS(200, "M005", "선택한 보낸 메일을 삭제했습니다."),
    DELETE_RECEIVE_MAIL_SUCCESS(200, "M006", "선택한 받은 메일을 삭제했습니다."),

    // Question
    GET_QUESTION_SUCCESS(200, "Q001", "이 주의 문답 질문을 조회했습니다."),
    GET_ANSWERS_SUCCESS(200, "Q002", "이 주의 문답에 대한 답변들을 조회했습니다."),
    GET_ANSWERED_FAMILY_SUCCESS(200, "Q003", "이 주의 문답에 참여한 가족들을 조회했습니다."),
    POST_ANSWER_SUCCESS(201, "Q004", "이 주의 문답 답변을 작성했습니다."),
    PUT_ANSWER_SUCCESS(200, "Q005", "이 주의 문답 답변을 수정했습니다."),
    DELETE_ANSWER_SUCCESS(200, "Q006", "이 주의 문답 답변을 삭제했습니다.")
    ;


    private final int status;
    private final String code;
    private final String message;
}
