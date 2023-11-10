package toy.five.triprecord.global.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private ErrorCode errorCode;

    public BaseException() {
    }

    public BaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BaseException(ErrorCode errorCode,String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BaseException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public int getStatusCode() {
        return this.errorCode.getStatusCode();
    }
}
