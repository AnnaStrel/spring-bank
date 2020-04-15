package ru.sgu.springbank.exception;

public class PermissionDenied extends RuntimeException {

    public PermissionDenied(String message) {
        super(message);
    }

}
