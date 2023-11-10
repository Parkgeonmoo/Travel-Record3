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
    JOURNEY_NO_EXIST(400,"해당 ID의 여행 정보가 없습니다.")

    ;

    private final int statusCode;
    private final String message;

}
