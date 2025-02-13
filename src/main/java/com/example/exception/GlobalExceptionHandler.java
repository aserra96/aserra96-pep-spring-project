package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class GlobalExceptionHandler {
    //Handle Bad Request Exception
    @ExceptionHandler(CustomException.BadRequestException.class) 
    public ResponseEntity<Object> handleBadRequestException(CustomException.BadRequestException x) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //Handle Conflict Exception
    @ExceptionHandler(CustomException.ConflictException.class) 
    public ResponseEntity<Object> handleConflictException(CustomException.ConflictException x) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();    
    }

    //Handle Not Found Exception
    @ExceptionHandler(CustomException.NotFoundException.class) 
    public ResponseEntity<Object> handleNotFoundException(CustomException.NotFoundException x) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();    
    }
    //Handle Unauthorized Exception
    @ExceptionHandler(CustomException.UnauthorizedException.class)
    public ResponseEntity<Void> handleUnauthorizedException(CustomException.UnauthorizedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    //Handle Any Generic exception
    @ExceptionHandler(Exception.class) 
    public ResponseEntity<Object> handleGenericException(Exception x) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("A Server Error");
    }
}
