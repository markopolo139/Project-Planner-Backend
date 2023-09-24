package pl.ms.projectoverview.web.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import pl.ms.projectoverview.app.exceptions.UserNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.ms.projectoverview.app.exceptions.*;

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

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> usernameNotFoundExceptionHandler(UserNotFoundException ex) {
        return mapToResponse(
                ApiError.builder()
                        .setSuggestedAction("Type correct username")
                        .setErrorMessage(ex.getMessage())
                        .setHttpStatus(HttpStatus.BAD_REQUEST)
                        .build()
        );
    }

    @ExceptionHandler(InvalidJwtTokenException.class)
    public ResponseEntity<Object> invalidJwtTokenExceptionHandler(InvalidJwtTokenException ex) {
        return mapToResponse(
                ApiError.builder()
                        .setSuggestedAction("Type correct token")
                        .setErrorMessage(ex.getMessage())
                        .setHttpStatus(HttpStatus.BAD_REQUEST)
                        .build()
        );
    }


    @ExceptionHandler(NotCurrentUserProjectException.class)
    public ResponseEntity<Object> NotCurrentUserProjectExceptionHandler(NotCurrentUserProjectException ex) {
        return mapToResponse(
                ApiError.builder()
                        .setSuggestedAction("Give project id that belongs to current user")
                        .setErrorMessage(ex.getMessage())
                        .setHttpStatus(HttpStatus.BAD_REQUEST)
                        .build()
        );
    }

    @ExceptionHandler(NotCurrentUserProjectPlanException.class)
    public ResponseEntity<Object> notCurrentUserProjectPlanExceptionHandler(NotCurrentUserProjectPlanException ex) {
        return mapToResponse(
                ApiError.builder()
                        .setSuggestedAction("Give project plan id that belongs to current user")
                        .setErrorMessage(ex.getMessage())
                        .setHttpStatus(HttpStatus.BAD_REQUEST)
                        .build()
        );
    }

    @ExceptionHandler(TitleNotFoundException.class)
    public ResponseEntity<Object> titleNotFoundExceptionHandler(TitleNotFoundException ex) {
        return mapToResponse(
                ApiError.builder()
                        .setSuggestedAction("Give title that exists")
                        .setErrorMessage(ex.getMessage())
                        .setHttpStatus(HttpStatus.BAD_REQUEST)
                        .build()
        );
    }

    @ExceptionHandler(NotificationTokenAlreadyAddedException.class)
    public ResponseEntity<Object> notificationTokenAlreadyAddedExceptionHandler(NotificationTokenAlreadyAddedException ex) {
        return mapToResponse(
                ApiError.builder()
                        .setSuggestedAction("Don't add this token")
                        .setErrorMessage(ex.getMessage())
                        .setHttpStatus(HttpStatus.BAD_REQUEST)
                        .build()
        );
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Object> dataAccessExceptionHandler(DataAccessException ex) {
        return mapToResponse(
                ApiError.builder()
                        .setErrorMessage("Error occurred during database operation (" + ex.getMessage() + ")")
                        .setHttpStatus(HttpStatus.BAD_REQUEST)
                        .build()
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> badCredentialsExceptionHandler(BadCredentialsException ex) {
        return mapToResponse(
                ApiError.builder()
                        .setErrorMessage("Invalid credentials")
                        .setHttpStatus(HttpStatus.BAD_REQUEST)
                        .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> defaultExceptionHandler(Exception ex) {
        return mapToResponse(
                ApiError.builder()
                        .setErrorMessage("Unexpected error occurred (" + ex.getMessage() + ")")
                        .setHttpStatus(HttpStatus.BAD_REQUEST)
                        .build()
        );
    }
}
