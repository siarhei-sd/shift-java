package com.nadzeya;

public class FullStatistic implements Statistic {
    int elementsNumber;
    int maxIntString = Integer.MIN_VALUE;
    int minIntString = Integer.MAX_VALUE;
    float maxFloat = Float.MIN_VALUE;
    float minFloat = Float.MAX_VALUE;
    int sumInt = 0;
    float sumFloat = 0;
    float avgInt = 0;
    float avgFloat = 0;
    String typeForStatistics = "none";

    public String getTypeForStatistics() {
        return typeForStatistics;
    }


    @Override
    public void printStatistic() {
        countAvg();
        System.out.print(" full statistic: elements = " + elementsNumber + "; ");
        switch (typeForStatistics) {
            case "int":
                System.out.println("min = " + minIntString + "; max = " + maxIntString + "; sum = " + sumInt + ", average = " + avgInt + ";");
                break;
            case "float":
                System.out.println("min = " + minFloat + "; max = " + maxFloat + "; sum = " + sumFloat + ", average = " + avgFloat + ";");
                break;
            case "string":
                System.out.println("minLength = " + minIntString + "; maxLength = " + maxIntString + ";");
                break;
            default:
                System.out.println("Произошла ошибка.");
        }
    }

    @Override
    public void updateStatistics(String str, String type) {
        elementsNumber++;
        typeForStatistics = type;
        if (type.equals("string")) {
            if (str.length() > maxIntString) maxIntString = str.length();
            if (str.length() < minIntString) minIntString = str.length();
        } else {
            if (type.equals("int")) {
                int number = Integer.parseInt(str);
                if (number > maxIntString) maxIntString = number;
                if (number < minIntString) minIntString = number;
                sumInt += number;

            } else if (type.equals("float")) {
                float number = Float.parseFloat(str);
                if (number > maxFloat) maxFloat = number;
                else if (number < minFloat) minFloat = number;
                sumFloat += number;

            }

        }

    }

    public float getAvgFloat() {
        countAvg();
        return avgFloat;
    }

    public float getAvgInt() {
        countAvg();
        return avgInt;
    }

    public float getSumFloat() {
        return sumFloat;
    }

    public int getSumInt() {
        return sumInt;
    }

    public float getMinFloat() {
        return minFloat;
    }

    public float getMaxFloat() {
        return maxFloat;
    }

    public int getMinIntString() {
        return minIntString;
    }

    public int getMaxIntString() {
        return maxIntString;
    }

    public int getElementsNumber() {
        return elementsNumber;
    }

    private void countAvg() {
        if (typeForStatistics.equals("float")) {
            avgFloat = sumFloat / elementsNumber;
        } else if (typeForStatistics.equals("int")) {
            avgInt = (float) sumInt / elementsNumber;
        }
    }

}