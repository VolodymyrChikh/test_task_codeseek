package com.volodymyrchikh.footballservice.exception;

public class TransferDeniedException extends RuntimeException {

    public TransferDeniedException(String message) {
        super(message);
    }
}
