package com.khoubyari.example.exception;

/**
 * for HTTP 400 errors
 */
public final class DataFormatException extends RuntimeException {

    public DataFormatException(String message) {
        super(message);
    }
}