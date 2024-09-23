package com.filter.utility;

import lombok.Getter;

@Getter
public enum DataType {
    INTEGER("integers.txt"),

    FLOAT("floats.txt"),

    STRING("strings.txt");

    private final String filename;

    DataType(String filename) {
        this.filename = filename;
    }

}
