package org.example;

public enum DataType {
    INTEGER, FLOAT, STRING;

    public static DataType determineType(String value) {
        if (value.matches("-?\\d+")) {
            return INTEGER;
        } else if (value.matches("-?\\d*\\.\\d+([eE]-?\\d+)?")) {
            return FLOAT;
        }
        return STRING;
    }
}

