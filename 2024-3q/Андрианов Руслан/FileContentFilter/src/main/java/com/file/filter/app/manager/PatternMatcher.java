package com.file.filter.app.manager;

public enum PatternMatcher {

    INTEGER_REGEX("^-?[0-9]\\d*$"),
    DOUBLE_REGEX("^[-+]?[0-9]*[.,][0-9]+(?:[eE][-+]?[0-9]+)?$");

    private final String regex;

    PatternMatcher(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}
