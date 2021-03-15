package br.com.mrit.pessoas.application.api.advice;

import br.com.mrit.pessoas.application.exception.ApiException;
import br.com.mrit.pessoas.application.model.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    private final String DEFAULT_ERROR_MESSAGE = "Contacte o administrador com o código %s";

    @ExceptionHandler({ApiException.class})
    public ResponseEntity<Object> handleApiException(
            ApiException ex, WebRequest request) {
        return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.valueOf(ex.getStatusCode()), request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String code = status.getReasonPhrase();
        String description = ex.getClass().getSimpleName();
        String message = ex.getMessage();
        if (ex instanceof ApiException) {
            description = ((ApiException) ex).getReason();
        } else if(ex instanceof MethodArgumentNotValidException){
            StringBuilder messageBuilder = new StringBuilder();
            BindingResult result = ((MethodArgumentNotValidException) ex).getBindingResult();
            List<FieldError> fieldErrors = result.getFieldErrors();
            for(FieldError error : fieldErrors){
                messageBuilder.append("Erro no campo: ").append(error.getField())
                        .append(" - mensagem de erro: ").append(error.getDefaultMessage());
                if(fieldErrors.indexOf(error) < (fieldErrors.size() -1)){
                    messageBuilder.append(" | ");
                }
            }
            message = messageBuilder.toString();
        }
        return super.handleExceptionInternal(ex, ErrorResponse
                .builder()
                .code(code)
                .description(description)
                .message(message)
                .build(), headers, status, request);
    }
}