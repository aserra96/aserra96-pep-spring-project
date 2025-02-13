package com.example.exception;

public class CustomException {
    //Used when the input is invalid
    public static class BadRequestException extends RuntimeException {
        public BadRequestException(String message) {super(message);}
    }
    //Used when theres a duplicate
    public static class ConflictException extends RuntimeException {
        public ConflictException(String message) {super(message);}
    }
    //Used when theres a Unauthorized login
    public static class UnauthorizedException extends RuntimeException {
        public UnauthorizedException(String message) {super(message);}
    }
    //Used when resource is not found
    public static class NotFoundException extends RuntimeException {
        public NotFoundException(String message) {super(message);}
    }
}
