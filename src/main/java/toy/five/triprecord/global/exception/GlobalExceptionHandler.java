package toy.five.triprecord.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;

import static toy.five.triprecord.global.exception.ValidationCode.*;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<String>> handleBaseException(BaseException e) {
        return new ResponseEntity<>(
                ApiResponse.fail(e.getStatusCode(), e.getMessage()),
                HttpStatus.valueOf(e.getStatusCode())
        );
    }




    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        FieldError fieldError = e.getBindingResult().getFieldError();
        String errorMessage = (fieldError != null) ? fieldError.getDefaultMessage() : null;

        BaseException baseException;

        log.info("에러메시지"+errorMessage);

        if (errorMessage != null) {
            if (TRIP_PARAMETER_ERROR.getMessage().equals(errorMessage)) {
                baseException = new BaseException(ErrorCode.TRIP_IVALID_UPDATE);
            } else if (TRIP_START_TIME_ERROR.getMessage().equals(errorMessage)) {
                baseException = new BaseException(ErrorCode.TRIP_INVALID_PARAMETER);
            } else if (TRIP_TIME_ERROR.getMessage().equals(errorMessage)) {
                baseException = new BaseException(ErrorCode.TRIP_INVALID_TIME);
            } else if (TRIP_PARAMETER_ONE_ERROR.getMessage().equals(errorMessage)) {
                baseException = new BaseException(ErrorCode.TRIP_INVALID_PARAMETER);
            } else if (ValidationCode.USER_PATCH_NO_PARAMETER_ERROR.getMessage().equals(errorMessage)) {
                baseException = new BaseException(ErrorCode.USER_PATCH_NO_PARAMETER_ERROR);
            } else if (ValidationCode.USER_EMAIL_MISMATCH_TYPE_ERROR.getMessage().equals(errorMessage)) {
                baseException = new BaseException(ErrorCode.USER_EMAIL_MISMATCH_TYPE_ERROR);
            } else if (ValidationCode.USER_EMPTY_EMAIL_ERROR.getMessage().equals(errorMessage)) {
                baseException = new BaseException(ErrorCode.USER_EMPTY_EMAIL_ERROR);
            } else if (ValidationCode.USER_EMPTY_PASSWORD_ERROR.getMessage().equals(errorMessage)) {
                baseException = new BaseException(ErrorCode.USER_EMPTY_PASSWORD_ERROR);
            } else if (ValidationCode.USER_EMPTY_NAME_ERROR.getMessage().equals(errorMessage)) {
                baseException = new BaseException(ErrorCode.USER_EMPTY_NAME_ERROR);
            } else if (ValidationCode.USER_COMMNON_PASSWORD_MISMATCH_ERROR.getMessage().equals(errorMessage)) {
                baseException = new BaseException(ErrorCode.USER_COMMNON_PASSWORD_MISMATCH_ERROR);
            }
            else {
                baseException = new BaseException(ErrorCode.TRIP_VALIDATE_ERROR);
            }
        } else {
            baseException = new BaseException(ErrorCode.TRIP_IVALID_UPDATE);
        }

        return new ResponseEntity<>(
                ApiResponse.fail(baseException.getStatusCode(), baseException.getMessage()),
                HttpStatus.valueOf(baseException.getStatusCode())
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<String>> handleClassCastException(HttpMessageNotReadableException e) {

        BaseException baseException = new BaseException(ErrorCode.TRIP_ENUM_ERROR);

        return new ResponseEntity<>(
                ApiResponse.fail(baseException.getStatusCode(), baseException.getMessage()),
                HttpStatus.valueOf(baseException.getStatusCode())
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<String>> handleAuthenticationException(AuthenticationException e) {
        log.info(log + "log내용");
        BaseException baseException = new BaseException(ErrorCode.USER_NO_APPROVE_ERROR);

        return new ResponseEntity<>(
                ApiResponse.fail(baseException.getStatusCode(), baseException.getMessage()),
                HttpStatus.valueOf(baseException.getStatusCode())
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<String>> handleAccessDeniedException(AccessDeniedException e) {
        BaseException baseException = new BaseException(ErrorCode.USER_NO_APPROVE_ERROR);

        return new ResponseEntity<>(
                ApiResponse.fail(baseException.getStatusCode(), baseException.getMessage()),
                HttpStatus.valueOf(baseException.getStatusCode())
        );
    }




}
