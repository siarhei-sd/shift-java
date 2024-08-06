package com.korona.filtering_utility.dto;
public class FullStatisticsDTO extends ShortStatisticsDTO {
    private int minInteger;
    private int maxInteger;
    private long sumInteger;
    private double avgInteger;

    private float minFloat;
    private float maxFloat;
    private double sumFloat;
    private double avgFloat;

    private long minLengthString;
    private long maxLengthString;

    public FullStatisticsDTO() {
        super();
    }

    public int getMinInteger() {
        return minInteger;
    }

    public void setMinInteger(int minInteger) {
        this.minInteger = minInteger;
    }

    public int getMaxInteger() {
        return maxInteger;
    }

    public void setMaxInteger(int maxInteger) {
        this.maxInteger = maxInteger;
    }

    public long getSumInteger() {
        return sumInteger;
    }

    public void setSumInteger(long sumInteger) {
        this.sumInteger = sumInteger;
    }

    public double getAvgInteger() {
        return avgInteger;
    }

    public void setAvgInteger(double avgInteger) {
        this.avgInteger = avgInteger;
    }

    public float getMinFloat() {
        return minFloat;
    }

    public void setMinFloat(float minFloat) {
        this.minFloat = minFloat;
    }

    public float getMaxFloat() {
        return maxFloat;
    }

    public void setMaxFloat(float maxFloat) {
        this.maxFloat = maxFloat;
    }

    public double getSumFloat() {
        return sumFloat;
    }

    public void setSumFloat(double sumFloat) {
        this.sumFloat = sumFloat;
    }

    public double getAvgFloat() {
        return avgFloat;
    }

    public void setAvgFloat(double avgFloat) {
        this.avgFloat = avgFloat;
    }

    public long getMinLengthString() {
        return minLengthString;
    }

    public void setMinLengthString(long minLengthString) {
        this.minLengthString = minLengthString;
    }

    public long getMaxLengthString() {
        return maxLengthString;
    }

    public void setMaxLengthString(long maxLengthString) {
        this.maxLengthString = maxLengthString;
    }

    @Override
    public String toString() {
        return "FullStatisticsDTO{" +
                "minInteger=" + minInteger +
                ", maxInteger=" + maxInteger +
                ", sumInteger=" + sumInteger +
                ", avgInteger=" + avgInteger +
                ", minFloat=" + minFloat +
                ", maxFloat=" + maxFloat +
                ", sumFloat=" + sumFloat +
                ", avgFloat=" + avgFloat +
                ", minLengthString=" + minLengthString +
                ", maxLengthString=" + maxLengthString +
                ", countInteger=" + getCountInteger() +
                ", countFloat=" + getCountFloat() +
                ", countString=" + getCountString() +
                ", fileNameForInteger=" + getFileNameForInteger() +
                ", fileNameForFloat=" + getFileNameForFloat() +
                ", fileNameForString=" + getFileNameForString() +
                '}';
    }
}