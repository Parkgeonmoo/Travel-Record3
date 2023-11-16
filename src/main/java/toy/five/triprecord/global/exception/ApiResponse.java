package toy.five.triprecord.global.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import toy.five.triprecord.global.common.StatusCode;

@Getter
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    private final String status;  // "success" 또는 "fail"
    private final int code;        // HTTP 상태 코드 또는 사용자 정의 코드
    private final T data;           // API에서 반환되는 데이터

    // 성공 응답 생성 메서드
    public static <T> ApiResponse<T> success(int code, T data) {
        return new ApiResponse<>("Success", code, data);
    }

    // 실패 응답 생성 메서드
    public static ApiResponse<String> fail(int code, String message) {
        return new ApiResponse<>(String.valueOf(StatusCode.FAIL), code, message);
    }
}
