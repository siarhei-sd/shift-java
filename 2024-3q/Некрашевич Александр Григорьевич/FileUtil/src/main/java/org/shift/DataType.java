package org.shift;

public enum DataType {
    STRING, INTEGER, FLOAT;

    public static DataType fromString(String str) {

        if (isInteger(str)) {
            return INTEGER;
        }

        if (isFloat(str)) {
            return FLOAT;
        }

        return STRING;
    }

    private static Boolean isInteger(String str) {
        return str.matches("-?\\d+");
    }

    private static Boolean isFloat(String str) {
        return str.matches("-?\\d+(\\.\\d+)?([eE][+-]?\\d+)?");
    }
}
