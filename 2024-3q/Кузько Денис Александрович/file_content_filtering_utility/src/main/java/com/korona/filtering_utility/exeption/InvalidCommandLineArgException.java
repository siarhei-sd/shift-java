package com.korona.filtering_utility.exeption;

public class InvalidCommandLineArgException extends IllegalArgumentException{

    public InvalidCommandLineArgException(String s) {
        super(s);
    }

    public InvalidCommandLineArgException(String message, Throwable cause) {
        super(message, cause);
    }
}
