package pl.ms.projectoverview.web.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.ms.projectoverview.app.exceptions.InvalidUserException;

import java.util.List;

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

    private final HttpServletRequest mRequest;

    public ExceptionsHandler(HttpServletRequest mRequest) {
        this.mRequest = mRequest;
    }

    private ResponseEntity<Object> mapToResponse(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request
    ) {
        List<ApiSubError> subErrors = ex.getBindingResult().getFieldErrors().stream().map( it ->
                ApiSubError.builder()
                        .setErrorMessage("validation failed for " + it.getRejectedValue() + "-" + it.getDefaultMessage())
                        .setSuggestedAction("Check rejected value")
                        .build()
        ).toList();

        return mapToResponse(
                ApiError.builder()
                        .setSuggestedAction("Check error sublist for more information")
                        .setErrorMessage("Error occurred during validation")
                        .setSubErrors(subErrors)
                        .setHttpStatus(HttpStatus.BAD_REQUEST)
                        .build()
        );
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request
    ) {
        return mapToResponse(
                ApiError.builder()
                        .setSuggestedAction("Give needed request body")
                        .setErrorMessage(ex.getMessage() == null ? ex.getMessage() : "Required request body")
                        .setHttpStatus(HttpStatus.BAD_REQUEST)
                        .build()
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> constraintViolationExceptionHandler(ConstraintViolationException ex) {
        List<ApiSubError> subErrors = ex.getConstraintViolations().stream().map( it ->
                ApiSubError.builder()
                        .setErrorMessage("validation failed for " + it.getInvalidValue() + "-" + it.getMessage())
                        .setSuggestedAction("Check rejected value")
                        .build()
        ).toList();

        return mapToResponse(
                ApiError.builder()
                        .setSuggestedAction("Check error sublist for more information")
                        .setErrorMessage("Error occurred during validation")
                        .setSubErrors(subErrors)
                        .setHttpStatus(HttpStatus.BAD_REQUEST)
                        .build()
        );
    }

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<Object> invalidUserExceptionHandler(InvalidUserException ex) {
        return mapToResponse(
                ApiError.builder()
                        .setSuggestedAction("Pass correct values for user creation")
                        .setErrorMessage(ex.getMessage())
                        .setHttpStatus(HttpStatus.BAD_REQUEST)
                        .build()
        );
    }
}
