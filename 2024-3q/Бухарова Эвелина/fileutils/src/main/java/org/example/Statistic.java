package org.example;

public class Statistic {
    String statisticType;

    int countLinesIntegers = 0;
    int countLinesDoubles = 0;
    int countLinesStrings = 0;

    int maxValueInteger = Integer.MIN_VALUE;
    int minValueInteger = Integer.MAX_VALUE;
    double maxValueDouble = Double.MIN_VALUE;
    double minValueDouble = Double.MAX_VALUE;
    int maxValueString = Integer.MIN_VALUE;
    int minValueString = Integer.MAX_VALUE;

    int sumInteger = 0;
    double sumDouble = 0;
    int averageInteger = 0;
    double averageDouble = 0;

    public Statistic(String statisticType) {
        this.statisticType = statisticType;
    }

    public void collectIntegers(int intLine) {
        sumInteger = intLine + sumInteger;
        if (minValueInteger > intLine) {
            minValueInteger = intLine;
        }
        if (maxValueInteger < intLine) {
            maxValueInteger = intLine;
        }
        countLinesIntegers++;
    }

    public void collectDouble(double doubleLine) {
        sumDouble = doubleLine + sumDouble;
        if (minValueDouble > doubleLine) {
            minValueDouble = doubleLine;
        }
        if (maxValueDouble < doubleLine) {
            maxValueDouble = doubleLine;
        }
        countLinesDoubles++;
    }

    public void collectString(int stringLine) {
        if (minValueString > stringLine) {
            minValueString = stringLine;
        }
        if (maxValueString < stringLine) {
            maxValueString = stringLine;
        }
        countLinesStrings++;

    }

    public void printStatistic() {
        if (statisticType == "-s") {
            System.out.println("Count of integer`s line is " + countLinesIntegers);
            System.out.println("Count of double`s line is " + countLinesDoubles);
            System.out.println("Count of string`s line is " + countLinesStrings);

        } else if (statisticType == "-f") {
            if (countLinesIntegers == 0) {
                System.out.println("Missing integers in the input file");
            } else {
                averageInteger = (sumInteger / countLinesIntegers);
                System.out.println("Min value of integer`s line is " + minValueInteger);
                System.out.println("Max value of integer`s line is " + maxValueInteger);
                System.out.println("Sum integers is " + sumInteger);
                System.out.println("Average integers is " + averageInteger);
            }
            if (countLinesDoubles == 0) {
                System.out.println("File does not contain Doubles numbers");
            } else {
                averageDouble = (sumDouble / countLinesDoubles);
                System.out.println("Min value of double`s line is " + minValueDouble);
                System.out.println("Max value of double`s line is " + maxValueDouble);
                System.out.println("Sum doubles is " + sumDouble);
                System.out.println("Average doubles is " + averageDouble);
            }
            if (countLinesStrings == 0) {
                System.out.println("File does not contain Strings");
            } else {
                System.out.println("Min value of String`s line is " + minValueString);
                System.out.println("Max value of String`s line is " + maxValueString);
            }
        }
    }
}
