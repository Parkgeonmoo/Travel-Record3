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
    ;


    private final String message;
}
