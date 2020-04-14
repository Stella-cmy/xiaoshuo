package com.oracle.xiaoshuo.common.exception;

public class ReachException extends Exception{
    public ReachException()
    {
        super();
    }
    public ReachException(String message) {
        super(message);
    }
    public ReachException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReachException(Throwable cause) {
        super(cause);
    }

    protected ReachException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
