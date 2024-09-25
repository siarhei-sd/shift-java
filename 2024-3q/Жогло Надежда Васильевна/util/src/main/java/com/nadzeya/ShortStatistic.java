package com.nadzeya;

public class ShortStatistic implements Statistic {
    int elementsNumber = 0;
    String typeForStatistics = "none";

    @Override
    public void printStatistic() {
        System.out.println(" short statistic: elements = " + elementsNumber);
    }

    public int getElementsNumber() {
        return elementsNumber;
    }

    public String getTypeForStatistics() {
        return typeForStatistics;
    }

    @Override
    public void updateStatistics(String str, String type) {
        typeForStatistics = type;
        elementsNumber++;
    }
}
