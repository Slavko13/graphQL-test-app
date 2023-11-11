package ru.test.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for handling various HTTP errors.
 * Author: Viacheslav Petrenko
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Exception handler for ResourceNotFoundException.
     * @param ex The ResourceNotFoundException instance.
     * @return ResponseEntity with an error message and NOT_FOUND status.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handler for BadRequestException.
     * @param ex The ResourceNotFoundException instance.
     * @return ResponseEntity with an error message and BAD_REQUEST status.
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(BadRequestException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for AccessDeniedException.
     * @param ex The AccessDeniedException instance.
     * @return ResponseEntity with an error message and FORBIDDEN status.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        return new ResponseEntity<>("Access denied", HttpStatus.FORBIDDEN);
    }

    /**
     * Exception handler for UnauthorizedException.
     * @param ex The UnauthorizedException instance.
     * @return ResponseEntity with an error message and UNAUTHORIZED status.
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException ex) {
        return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    /**
     * Exception handler for InternalServerErrorException.
     * @param ex The InternalServerErrorException instance.
     * @return ResponseEntity with an error message and INTERNAL_SERVER_ERROR status.
     */
    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<String> handleInternalServerErrorException(InternalServerErrorException ex) {
        return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
