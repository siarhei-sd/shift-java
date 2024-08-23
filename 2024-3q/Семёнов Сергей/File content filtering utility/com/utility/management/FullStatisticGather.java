package com.utility.management;

public class FullStatisticGather extends StatisticGather {

    private double doubleMax;
    private double doubleMin=Double.MAX_VALUE;
    private double doubleSum;

    private long longMax;
    private long longMin=Long.MAX_VALUE;
    private long longSum;

    private int stringMin=Integer.MAX_VALUE;
    private int stringMax;

    @Override
    public void stringStatistics(String string) {
        stringElements++;
        stringMin = Math.min(stringMin, string.length());
        stringMax = Math.max(stringMax, string.length());
    }

    @Override
    public void longStatistics(String noLong) {
        long aLong =Long.parseLong(noLong);
        longElements++;
        longMin = Math.min(longMin, aLong);
        longMax = Math.max(longMax, aLong);
        longSum += aLong;
    }

    @Override
    public void doubleStatistics(String noDouble) {
        double aDouble =Double.parseDouble(noDouble);
        doubleElements++;
        doubleMin = Math.min(doubleMin, aDouble);
        doubleMax = Math.max(doubleMax, aDouble);
        doubleSum += aDouble;
    }

    @Override
    public String getStatistics(String longFile, String doubleFile, String stringFile){
        double longAverage = longSum / (double) longElements;
        double doubleAverage = doubleSum / doubleElements;
        String Statistics="";
        if (longElements > 0) {
            Statistics+=longFile+ " full statistic: elements= " + longElements+
            " max= "+longMax+" min= "+longMin+" sum= "+longSum+" average= "+ longAverage +"\n";
        }
        if (doubleElements > 0) {
            Statistics+=doubleFile+ " full statistic: elements= " + doubleElements+
            " max= "+doubleMax+" min= "+doubleMin+" sum= "+doubleSum+" average= "+ doubleAverage +"\n";
        }
        if (stringElements > 0) {
            Statistics+=stringFile+ " full statistic: elements= " + stringElements+
            " max= "+stringMax+" min= "+stringMin+"\n";
        }
        if(Statistics.isEmpty()) return "No elements for statistics";
        return Statistics;
    }
}
