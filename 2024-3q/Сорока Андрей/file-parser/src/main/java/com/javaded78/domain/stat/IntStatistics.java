package com.javaded78.domain.stat;

public class IntStatistics extends ShortStatistics {

    private long max = Long.MIN_VALUE;
    private long min = Long.MAX_VALUE;
    private long sum;

    public IntStatistics(final String fileName) {
        super(fileName);
    }

    public void update(final long value) {
        super.setCount();
        this.max = Math.max(this.max, value);
        this.min = Math.min(this.min, value);
        this.sum += value;
    }

    public long getMax() {
        return max;
    }

    public long getMin() {
        return min;
    }

    public long getSum() {
        return sum;
    }

    public double getAvg() {
        return super.getCount() == 0 ? 0 : (double) this.sum / super.getCount();
    }

    @Override
    public String display() {
        return super.getFileName() +
               " full statistic: elements = " + super.getCount() +
               ", max = " + max +
               ", min = " + min +
               ", sum = " + sum +
               ", average = " + getAvg();
    }
}
