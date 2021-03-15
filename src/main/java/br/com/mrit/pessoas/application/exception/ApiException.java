package br.com.mrit.pessoas.application.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class ApiException extends Exception {

    private final String code;
    private final String reason;
    private final Integer statusCode;


    @Builder
    public ApiException(String code, String reason, String message, Integer statusCode) {
        super(message);
        this.code = code;
        this.reason = reason;
        this.statusCode = statusCode;
    }


    public static ApiException badRequest(String reason, String message){
        return new ApiException1(HttpStatus.BAD_REQUEST.getReasonPhrase(),reason, message,HttpStatus.BAD_REQUEST.value());
    }
    public static ApiException unauthorized(String reason, String message){
        return new ApiException1(HttpStatus.UNAUTHORIZED.getReasonPhrase(),reason, message,HttpStatus.UNAUTHORIZED.value());
    }
    public static ApiException forbidden(String reason, String message){
        return new ApiException1(HttpStatus.FORBIDDEN.getReasonPhrase(),reason, message,HttpStatus.FORBIDDEN.value());
    }
    public static ApiException notFound(String reason, String message){
        return new ApiException1(HttpStatus.NOT_FOUND.getReasonPhrase(),reason, message,HttpStatus.NOT_FOUND.value());
    }
    public static ApiException conflict(String reason, String message){
        return new ApiException1(HttpStatus.CONFLICT.getReasonPhrase(),reason, message,HttpStatus.CONFLICT.value());
    }
    public static ApiException preconditionFailed(String reason, String message){
        return new ApiException1(HttpStatus.PRECONDITION_FAILED.getReasonPhrase(),reason, message,HttpStatus.PRECONDITION_FAILED.value());
    }

    public static ApiException internalError(String reason, String message){
        return new ApiException1(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),reason, message,HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
    public static ApiException notImplemented(String reason, String message){
        return new ApiException1(HttpStatus.NOT_IMPLEMENTED.getReasonPhrase(),reason, message,HttpStatus.NOT_IMPLEMENTED.value());
    }
    public static ApiException serviceUnavailable(String reason, String message){
        return new ApiException1(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase(),reason, message,HttpStatus.SERVICE_UNAVAILABLE.value());
    }

}