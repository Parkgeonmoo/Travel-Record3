package toy.five.triprecord.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ValidationCode {

    TRIP_START_TIME_ERROR("시작 시간이  필요합니다."),
    TRIP_TIME_ERROR("시작 시간이 종료 시간보다 뒤입니다."),
    TRIP_PARAMETER_ERROR("최소 하나의 필드가 입력되어야 합니다."),
    TRIP_PARAMETER_ONE_ERROR("필드가 전부 채워져야 합니다."),
    USER_PATCH_NO_PARAMETER_ERROR("패스워드와 이름 중 하나를 입력해주세요."),
    USER_EMAIL_MISMATCH_TYPE_ERROR("이메일 형식으로 입력해주세요."),
    USER_EMPTY_EMAIL_ERROR("이메일을 입력해주세요."),
    USER_EMPTY_PASSWORD_ERROR("비밀번호를 입력해주세요."),
    USER_EMPTY_NAME_ERROR("이름을 입력해주세요."),
    USER_COMMNON_PASSWORD_MISMATCH_ERROR("패스워드는 대소문자 1개와 숫자를 포함한 6~10자리로 써주세요."),
    ;


    private final String message;
}
