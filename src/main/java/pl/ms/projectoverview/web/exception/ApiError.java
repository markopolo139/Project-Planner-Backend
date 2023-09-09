package pl.ms.projectoverview.web.exception;

import org.springframework.http.HttpStatus;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ApiError {
    public static final String DEFAULT_ACTION = "Contact server admin";
    public static final String DEFAULT_ERROR_MESSAGE = "Unexpected error occurred";
    public static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    private final String suggestedAction;
    private final String errorMessage;
    private final List<ApiSubError> subErrors;
    private final HttpStatus httpStatus;

    public ApiError(String suggestedAction, String errorMessage, List<ApiSubError> subErrors, HttpStatus httpStatus) {
        this.suggestedAction = suggestedAction;
        this.errorMessage = errorMessage;
        this.subErrors = subErrors;
        this.httpStatus = httpStatus;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String suggestedAction = DEFAULT_ACTION;
        private String errorMessage = DEFAULT_ERROR_MESSAGE;
        private List<ApiSubError> subErrors = Collections.emptyList();
        private HttpStatus httpStatus = DEFAULT_HTTP_STATUS;

        public Builder setSuggestedAction(String suggestedAction) {
            this.suggestedAction = suggestedAction;
            return this;
        }

        public Builder setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public Builder setSubErrors(List<ApiSubError> subErrors) {
            this.subErrors = subErrors;
            return this;
        }

        public Builder setHttpStatus(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        public ApiError build() {
            return new ApiError(suggestedAction, errorMessage, subErrors, httpStatus);
        }
    }

    public String getSuggestedAction() {
        return suggestedAction;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public List<ApiSubError> getSubErrors() {
        return subErrors;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiError apiError = (ApiError) o;
        return Objects.equals(getSuggestedAction(), apiError.getSuggestedAction())
                && Objects.equals(getErrorMessage(), apiError.getErrorMessage())
                && Objects.equals(getSubErrors(), apiError.getSubErrors())
                && getHttpStatus() == apiError.getHttpStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSuggestedAction(), getErrorMessage(), getSubErrors(), getHttpStatus());
    }
}
