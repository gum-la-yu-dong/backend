package com.gumlayudong.gumlayudongbackend.exception;

@Getter
public abstract class CommonException extends RuntimeException {
    protected CommonException(String message) {
        super(message);
    }
}
