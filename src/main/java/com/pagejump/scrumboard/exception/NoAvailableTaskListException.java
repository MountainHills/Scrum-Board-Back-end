package com.pagejump.scrumboard.exception;

public class NoAvailableTaskListException extends RuntimeException{

    public NoAvailableTaskListException() {
        super();
    }

    public NoAvailableTaskListException(String message) {
        super(message);
    }

    public NoAvailableTaskListException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoAvailableTaskListException(Throwable cause) {
        super(cause);
    }

    protected NoAvailableTaskListException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
