package net.hackyourfuture.backend.week6.postify.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TrackNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleTrackNotFound(
            TrackNotFoundException exception) {

        return Map.of(
                "message",
                exception.getMessage()
        );
    }

    @ExceptionHandler(LyricsNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleLyricsNotFound(
            LyricsNotFoundException exception) {

        return Map.of(
                "message",
                exception.getMessage()
        );
    }
}