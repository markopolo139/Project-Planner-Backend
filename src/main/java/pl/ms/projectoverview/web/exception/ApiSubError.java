package pl.ms.projectoverview.web.exception;

import java.util.Objects;

public class ApiSubError {

    private final String suggestedAction;
    private final String errorMessage;

    public ApiSubError(String suggestedAction, String errorMessage) {
        this.suggestedAction = suggestedAction;
        this.errorMessage = errorMessage;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String suggestedAction = "";
        private String errorMessage = "";

        public Builder setSuggestedAction(String suggestedAction) {
            this.suggestedAction = suggestedAction;
            return this;
        }

        public Builder setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public ApiSubError build() {
            return new ApiSubError(suggestedAction, errorMessage);
        }
    }

    public String getSuggestedAction() {
        return suggestedAction;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiSubError that = (ApiSubError) o;
        return Objects.equals(getSuggestedAction(), that.getSuggestedAction()) && Objects.equals(getErrorMessage(), that.getErrorMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSuggestedAction(), getErrorMessage());
    }
}
