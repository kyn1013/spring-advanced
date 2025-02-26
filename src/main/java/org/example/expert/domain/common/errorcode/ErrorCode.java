package org.example.expert.domain.common.errorcode;


public enum ErrorCode {
    // AuthException
    EMAIL_ALREADY_EXISTS("이미 사용 중인 이메일입니다."),
    USER_NOT_FOUND_BY_EMAIL("해당 이메일로 등록된 유저가 없습니다."),
    INVALID_PASSWORD("비밀번호가 잘못되었습니다."),
    AUTH_AND_AUTHUSER_REQUIRED("@Auth와 AuthUser 타입은 함께 사용되어야 합니다."),

    // ServerException
    WEATHER_DATA_FETCH_FAILED("날씨 데이터를 가져오는데 실패했습니다."),
    NO_WEATHER_DATA_FOUND("날씨 데이터가 없습니다."),
    NO_WEATHER_DATA_FOR_TODAY("오늘에 해당하는 날씨 데이터를 찾을 수 없습니다."),
    TOKEN_NOT_FOUND("토큰을 찾을 수 없습니다."),

    // InvalidRequestException
    TODO_NOT_FOUND("해당하는 할일이 없습니다."),
    MANAGER_NOT_FOUND("관리자가 없습니다"),
    NOT_ASSIGNED_TO_SCHEDULE("해당 일정에 등록된 담당자가 아닙니다."),
    CANNOT_REGISTER_AS_SELF("일정 작성자는 본인을 담당자로 등록할 수 없습니다."),
    USER_NOT_FOUND("유저가 존재하지 않습니다."),
    INVALID_SCHEDULE_CREATOR("담당자를 등록하려고 하는 유저가 일정을 만든 유저가 유효하지 않습니다."),
    INVALID_USER_ROLE("유효하지 않은 UserRole입니다."),
    USING_PASSWORD("이미 사용 중인 비밀번호 입니다.");


    private String message;
    private ErrorCode(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
