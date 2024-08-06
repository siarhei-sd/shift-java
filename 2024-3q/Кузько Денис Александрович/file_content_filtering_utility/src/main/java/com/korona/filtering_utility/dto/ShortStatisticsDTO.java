package com.korona.filtering_utility.dto;

public class ShortStatisticsDTO {

    private String fileNameForInteger;
    private String fileNameForFloat;
    private String fileNameForString;

    private long countInteger;
    private long countFloat;
    private long countString;

    public ShortStatisticsDTO() {
    }

    public String getFileNameForInteger() {
        return fileNameForInteger;
    }

    public void setFileNameForInteger(String fileNameForInteger) {
        this.fileNameForInteger = fileNameForInteger;
    }

    public String getFileNameForFloat() {
        return fileNameForFloat;
    }

    public void setFileNameForFloat(String fileNameForFloat) {
        this.fileNameForFloat = fileNameForFloat;
    }

    public String getFileNameForString() {
        return fileNameForString;
    }

    public void setFileNameForString(String fileNameForString) {
        this.fileNameForString = fileNameForString;
    }

    public long getCountInteger() {
        return countInteger;
    }

    public void setCountInteger(long countInteger) {
        this.countInteger = countInteger;
    }

    public long getCountFloat() {
        return countFloat;
    }

    public void setCountFloat(long countFloat) {
        this.countFloat = countFloat;
    }

    public long getCountString() {
        return countString;
    }

    public void setCountString(long countString) {
        this.countString = countString;
    }

    @Override
    public String toString() {
        return "ShortStatisticsDTO{" +
                "fileNameForInteger='" + fileNameForInteger + '\'' +
                ", fileNameForFloat='" + fileNameForFloat + '\'' +
                ", fileNameForString='" + fileNameForString + '\'' +
                ", countInteger=" + countInteger +
                ", countFloat=" + countFloat +
                ", countString=" + countString +
                '}';
    }
}
