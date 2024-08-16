package by.shift.matveenko.data;

public abstract class DataStatistics {
    protected final StatisticsTypes statisticsTypes;
    protected long amount;

    public DataStatistics(StatisticsTypes statisticsTypes) {
        this.statisticsTypes = statisticsTypes;
    }

    public long getAmount() {
        return amount;
    }

    public abstract void addData(String data);
}