package com.task.game.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RequestException extends RuntimeException {

    private final HttpStatus status;

    public RequestException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
