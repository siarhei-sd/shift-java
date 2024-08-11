package com.javaded78.domain.stat;

public class ShortStatistics extends Statistics {

    private long count;

    public ShortStatistics(final String fileName) {
        super(fileName);
    }

    public long getCount() {
        return count;
    }

    public void setCount() {
        this.count++;
    }

    @Override
    public String display() {
        return getFileName() + " short statistic: elements = " + count;
    }
}
