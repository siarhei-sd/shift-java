package org.dezzzl.statistics;

public class StringFullStatistics extends ShortStatistics {
    private int shortestStringLength = Integer.MAX_VALUE;
    private int longestStringLength = Integer.MIN_VALUE;


    @Override
    public void updateStatistics(Object line) {
        if (line == null) return;
        if (line instanceof String str) {
            updateNumberOfElements();
            updateShortestStringLength(str);
            updateLongestStringLength(str);
        }
    }

    @Override
    public void printStatistics() {
        String fullStatistic = String.format(
                "%s full statistic: elements = %d; min length = %d; max length = %d",
                this.getPathToFile(), this.numberOfElements,
                (this.shortestStringLength == Integer.MAX_VALUE ? null : this.shortestStringLength),
                (this.longestStringLength == Integer.MIN_VALUE ? null : this.longestStringLength)
        );
        System.out.println(fullStatistic);
    }

    private void updateShortestStringLength(String line) {
        if (line.length() < this.shortestStringLength) this.shortestStringLength = line.length();
    }

    private void updateLongestStringLength(String line) {
        if (line.length() > this.longestStringLength) this.longestStringLength = line.length();
    }
}
