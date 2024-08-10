package org.dezzzl.statistics;

public class ShortStatistics extends Statistics<Object> {
    protected int numberOfElements;


    @Override
    public void updateStatistics(Object value) {
        updateNumberOfElements();
    }

    @Override
    public void printStatistics() {
        String shortStatistic = String.format("%s short statistic: elements = %d",
                this.getPathToFile(),
                this.numberOfElements);
        System.out.println(shortStatistic);
    }

    protected void updateNumberOfElements() {
        this.numberOfElements++;
    }


}
