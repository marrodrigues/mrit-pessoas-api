package br.com.mrit.pessoas.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends Exception {

    private final String code;
    private final String reason;
    private final Integer statusCode;

    public ApiException(String code, String reason, String message, Integer statusCode) {
        super(message);
        this.code = code;
        this.reason = reason;
        this.statusCode = statusCode;
    }

    public static ApiException badRequest(String reason, String message){
        return new ApiException(HttpStatus.BAD_REQUEST.getReasonPhrase(),reason, message,HttpStatus.BAD_REQUEST.value());
    }
    public static ApiException notFound(String reason, String message){
        return new ApiException(HttpStatus.NOT_FOUND.getReasonPhrase(),reason, message,HttpStatus.NOT_FOUND.value());
    }
    public static ApiException conflict(String reason, String message){
        return new ApiException(HttpStatus.CONFLICT.getReasonPhrase(),reason, message,HttpStatus.CONFLICT.value());
    }
    public static ApiException internalError(String reason, String message){
        return new ApiException(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),reason, message,HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}