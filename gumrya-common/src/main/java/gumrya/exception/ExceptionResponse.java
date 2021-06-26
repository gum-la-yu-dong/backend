package gumrya.exception;

import lombok.Getter;

@Getter
public class ExceptionResponse {

    private final String message;

    public ExceptionResponse(String message) {
        this.message = message;
    }
}
