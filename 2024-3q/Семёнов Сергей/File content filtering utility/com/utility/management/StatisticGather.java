package com.utility.management;

public class StatisticGather {

    protected long longElements;
    protected long doubleElements;
    protected long stringElements;

    public void stringStatistics(String string) {
        stringElements++;
    }

    public void longStatistics(String noLong) {
        longElements++;
    }

    public void doubleStatistics(String noDouble) {
        doubleElements++;
    }

    public String getStatistics(String longFile, String doubleFile, String stringFile) {
        String Statistics="";
        if (longElements > 0) {
            Statistics+= longFile + " short statistic: elements= " + longElements+"\n";
        }
        if (doubleElements > 0) {
            Statistics+= doubleFile + " short statistic: elements= " + doubleElements+"\n";
        }
        if (stringElements > 0) {
            Statistics+= stringFile + " short statistic: elements= " + stringElements+"\n";
        }
        if(Statistics.isEmpty()) return "No elements for statistics";
        return Statistics;
    }
}
