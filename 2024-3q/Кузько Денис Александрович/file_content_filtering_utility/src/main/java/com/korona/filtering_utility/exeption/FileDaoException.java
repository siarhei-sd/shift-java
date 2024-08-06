package com.korona.filtering_utility.exeption;

public class FileDaoException extends RuntimeException{
    public FileDaoException(String message) {
        super(message);
    }

    public FileDaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
