package org.srmzhk;

public class Statistic {
    String[] outputFiles = {};
    private char statType;

    //for short stat
    private int integersAmount;
    private int floatsAmount;
    private int stringsAmount;
    //for full stat
    private long integersMaxValue;
    private long integersMinValue;
    private long integersSum;
    private long integersAvg;
    private float floatsMaxValue;
    private float floatsMinValue;
    private float floatsSum;
    private float floatsAvg;
    private int stringsMaxLength;
    private int stringsMinLength;

    public Statistic(String[] outputFiles, char statType) {
        this.outputFiles = outputFiles;
        this.statType = statType;
    }

    protected void countInteger(String s) {
        integersAmount++;
        try {
            long buf = Long.parseLong(s);
            if (integersAmount == 1) {
                integersMaxValue = buf;
                integersMinValue = buf;
            }
            else {
                if (buf > integersMaxValue)
                    integersMaxValue = buf;
                else if (buf < integersMinValue)
                    integersMinValue = buf;
            }
            integersSum += buf;
        } catch (Exception e) {
            System.out.println("The number is too huge or too small: " + s);
        }
    }

    protected void countFloat(String s) {
        floatsAmount++;
        try {
            float buf = Float.parseFloat(s);
            if (floatsAmount == 1) {
                floatsMaxValue = buf;
                floatsMinValue = buf;
            }
            else {
                if (buf > floatsMaxValue)
                    floatsMaxValue = buf;
                else if (buf < floatsMinValue)
                    floatsMinValue = buf;
            }
            floatsSum += buf;
        } catch (Exception e) {
            System.out.println("The number is too huge or too small: " + s);
        }
    }

    protected void countString(String s) {
        stringsAmount++;
        if (stringsAmount == 1) {
            stringsMaxLength = s.length();
            stringsMinLength = s.length();
        }
        else {
            if (s.length() > stringsMaxLength)
                stringsMaxLength = s.length();
            else if (s.length() < stringsMinLength)
                stringsMinLength = s.length();
        }
    }

    private void countAvg() {
        if(integersAmount > 0)
            integersAvg = integersSum / integersAmount;
        if(floatsAmount > 0)
            floatsAvg = floatsSum / floatsAmount;
    }

    public void showStat() {
        countAvg();
        if(statType == 's')
            showShortStat();
        else if (statType == 'f')
            showFullStat();
    }

    private void showShortStat() {
        System.out.println(outputFiles[0] + " short statistic: elements = " + integersAmount);
        System.out.println(outputFiles[1] + " short statistic: elements = " + floatsAmount);
        System.out.println(outputFiles[2] + " short statistic: elements = " + stringsAmount);
    }

    private void showFullStat() {
        System.out.println(
                outputFiles[0]
                        + " full statistic: elements = " + integersAmount + "; "
                        + "min: " + integersMinValue + "; "
                        + "max: " + integersMaxValue + "; "
                        + "sum: " + integersSum + ", "
                        + "average: " + integersAvg + ";"
        );
        System.out.println(
                outputFiles[1]
                        + " full statistic: elements = " + floatsAmount + "; "
                        + "min: " + floatsMinValue + "; "
                        + "max: " + floatsMaxValue + "; "
                        + "sum: " + floatsSum + ", "
                        + "average: " + floatsAvg + ";"
        );
        System.out.println(
                outputFiles[2]
                        + " full statistic: elements = " + stringsAmount + "; "
                        + "min length: " + stringsMinLength + "; "
                        + "max length: " + stringsMaxLength + "; "
        );
    }
}
