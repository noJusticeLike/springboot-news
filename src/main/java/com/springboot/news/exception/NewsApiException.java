package com.springboot.news.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NewsApiException extends RuntimeException {
    private HttpStatus status;
    private String message;

    public NewsApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }
}
