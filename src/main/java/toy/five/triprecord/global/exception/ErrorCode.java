package toy.five.triprecord.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //Trip
    TRIP_NO_EXIST(400,"해당 ID의 여행 정보가 없습니다."),
    TRIP_INVALID_TIME(400,"시간이 올바르게 입력되지 않았습니다."),
    TRIP_INVALID_PARAMETER(400,"입력하신 여행의 정보가 전부 입력되지 않았습니다."),
    TRIP_IVALID_UPDATE(400,"업데이트하고자 하는 여행의 정보가 한 개는 필요합니다."),
    TRIP_SERVER_ERROR(400,"서버에 문제가 생겨 잠시 후 다시 시도해주세요"),
    TRIP_ENUM_ERROR(400,"정확한 타입으로 입력해주세요."),
    TRIP_VALIDATE_ERROR(400,"입력하신 데이터 검증에 실패하였습니다."),

    //Journey
    JOURNEY_NO_EXIST(400,"해당 ID의 여행 정보가 없습니다."),

    //User
    USER_NO_EXIST(400, "해당 ID의 사용자 정보가 없습니다."),
    USER_PATCH_NO_PARAMETER_ERROR(400,"패스워드와 이름 중 하나를 입력해주세요."),
    USER_EMAIL_MISMATCH_TYPE_ERROR(400,"이메일 형식으로 입력해주세요."),
    USER_EMPTY_EMAIL_ERROR(400,"이메일을 입력해주세요."),
    USER_EMPTY_PASSWORD_ERROR(400,"비밀번호를 입력해주세요."),
    USER_EMPTY_NAME_ERROR(400,"이름을 입력해주세요."),
    USER_CAN_NOT_FIND_EMAIL(400,"조회하고자 하는 정보가 없습니다."),
    USER_EMAIL_DUPULICATE_ERROR(400,"이미 사용중인 이메일입니다."),
    USER_COMMNON_PASSWORD_MISMATCH_ERROR(400,"패스워드는 대소문자 1개와 숫자를 포함한 6~10자리로 써주세요."),
    USER_NO_APPROVE_ERROR(403,"인증에 실패했습니다."),


    //Like
    LIKE_NO_EXIST(400, "해당 여행을 좋아요하지 않은 상태입니다."),
    ALREADY_WISH(400, "해당 여행을 이미 좋아요를 한 상태입니다."),

    //Security
    JWT_TOKEN_EXPIRED(403,"인증이 만료되어 재로그인해주세요."),


    //Comment
    COMMENT_NO_EXIST(400, "해당 댓글이 존재하지 않습니다."),

    //MAP
    NO_RESULT_SEARCH_LOCATION(400, "해당 키워드에 대한 검색결과가 존재하지 않습니다.")
    ;

    private final int statusCode;
    private final String message;

}
