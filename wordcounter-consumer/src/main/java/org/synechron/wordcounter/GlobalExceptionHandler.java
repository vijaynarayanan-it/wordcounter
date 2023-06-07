package org.synechron.wordcounter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import feign.FeignException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(FeignException.class)
    public void handleFeignStatusException(FeignException e, HttpServletResponse response) {
        response.setStatus(e.status());
    }
}
