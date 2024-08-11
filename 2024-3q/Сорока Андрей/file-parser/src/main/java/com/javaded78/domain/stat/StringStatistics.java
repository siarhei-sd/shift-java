package com.javaded78.domain.stat;

import com.javaded78.exception.WriteFileException;

public class StringStatistics extends ShortStatistics {

    private int shortest;
    private int longest;

    public StringStatistics(final String fileName) {
        super(fileName);
    }

    public void update(final String value) {
        super.setCount();
        this.shortest = super.getCount() == 1 ? value.length() : Math.min(this.shortest, value.length());
        this.longest = Math.max(this.longest, value.length());
    }


    public int getShortest() {
        return shortest;
    }

    public int getLongest() {
        return longest;
    }

    @Override
    public String display() {
        String shortestDesc = shortest > 1 ? " symbols" : " symbol";
        String longestDesc = longest > 1 ? " symbols" : " symbol";

        return super.getFileName() +
               " full statistic: elements = " + super.getCount() +
               ", shortest = " + shortest + shortestDesc +
               ", longest = " + longest + longestDesc;
    }
}
