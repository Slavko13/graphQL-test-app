package ru.test.app.exception;

/**
 * Exception thrown when a requested resource is not found (HTTP 404).
 * Author: Viacheslav Petrenko
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(final String message) {
        super(message);
    }
}

