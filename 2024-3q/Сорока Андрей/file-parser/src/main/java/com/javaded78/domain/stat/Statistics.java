package com.javaded78.domain.stat;

public abstract class Statistics {

    protected final String fileName;

    public Statistics(final String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public abstract String display();
}
