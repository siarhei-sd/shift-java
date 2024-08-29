package ru.korona.enums;

public enum Types {
    INTEGERS("integers"),
    FLOATS("floats"),
    STRINGS("strings");

    private final String type;

    Types(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
