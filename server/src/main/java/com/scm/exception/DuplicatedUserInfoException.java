package com.scm.exception;

public class DuplicatedUserInfoException extends RuntimeException {

    public DuplicatedUserInfoException(String message) {
        super(message);
    }
}
