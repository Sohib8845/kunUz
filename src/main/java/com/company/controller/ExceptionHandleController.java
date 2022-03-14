package com.company.controller;

import com.company.exeptions.BadRequestException;
import com.company.exeptions.ForbiddenException;
import com.company.exeptions.ItemNotFoundException;
import com.company.exeptions.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

public class ExceptionHandleController {
    @ExceptionHandler({ItemNotFoundException.class,BadRequestException.class})
    public ResponseEntity<?> handleException(RuntimeException ex){
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler({ForbiddenException.class})
    public ResponseEntity<?> handleException(ForbiddenException ex){
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler({UnauthorizedException.class})
    public ResponseEntity<?> handleException(UnauthorizedException ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

}
