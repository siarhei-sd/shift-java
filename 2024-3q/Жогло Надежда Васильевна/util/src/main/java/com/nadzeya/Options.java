package com.nadzeya;

public enum Options {
    OUTPUT("-o"),
    PREFIX("-p"),
    ADD("-a"),
    SHORTSTAT("-s"),
    FULLSTAT("-f");
    private int amountOfOptions;
    private final String option;
    Options(String s) {
        this.option = s;
    }

    public String getOption() {
        return option;
    }

    public int getAmountOfOptions() {
        return Options.values().length;
    }
}
