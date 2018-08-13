package com.nilson.account.exceptions;

public class ExceptionBuild {

    private int status;
    private String message;

    private ExceptionBuild() {
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public static final class Builder {
        private int status;
        private String message;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public ExceptionBuild build() {
            ExceptionBuild resourceNotFoundDetails = new ExceptionBuild();
            resourceNotFoundDetails.message = this.message;
            resourceNotFoundDetails.status = this.status;
            return resourceNotFoundDetails;
        }
    }
}
