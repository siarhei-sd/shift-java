package org.egorsemenovv.statistics;

public class StringStatistics extends Statistics<String>{

    protected StringStatistics(String fileName) {
        super(fileName);
    }

    public int getMinLength(){
        String minElement = getMinElement();
        if (minElement!=null){
            return minElement.length();
        }
        return 0;
    }

    public int getMaxLength(){
        String maxElement = getMaxElement();
        if (maxElement!=null){
            return maxElement.length();
        }
        return 0;
    }
}
