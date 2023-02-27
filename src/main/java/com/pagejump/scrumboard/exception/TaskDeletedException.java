package com.pagejump.scrumboard.exception;

public class TaskDeletedException extends RuntimeException{

    public TaskDeletedException() {
        super();
    }

    public TaskDeletedException(String message) {
        super(message);
    }

    public TaskDeletedException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskDeletedException(Throwable cause) {
        super(cause);
    }

    protected TaskDeletedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
