package com.automation.Exceptions;

public class FileNotFoundException extends RuntimeException {

    public FileNotFoundException(String reason) {
        super(reason);
    }
}
