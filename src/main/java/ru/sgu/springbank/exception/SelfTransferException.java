package ru.sgu.springbank.exception;

public class SelfTransferException extends RuntimeException {

    public SelfTransferException(String message) {
        super(message);
    }

}
