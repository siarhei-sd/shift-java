package by.shift.matveenko.data;

public enum DataTypes {
    INTEGER, DOUBLE, STRING;

    public String toString() {
        switch (this) {
            case INTEGER -> {
                return "integers";
            }
            case DOUBLE -> {
                return "floats";
            }
            case STRING -> {
                return "strings";
            }
            default -> throw new IllegalArgumentException("Invalid data type");
        }
    }
}