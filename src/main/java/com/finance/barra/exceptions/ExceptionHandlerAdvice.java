package com.finance.barra.exceptions;

import com.finance.barra.core.DynamicDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(ControllerException.class)
    public ResponseEntity handleException(ControllerException e) {
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(DynamicDto.of()
                        .with("message", e.getMessage())
                        .with("status", e.getHttpStatus().value()));
    }
}
