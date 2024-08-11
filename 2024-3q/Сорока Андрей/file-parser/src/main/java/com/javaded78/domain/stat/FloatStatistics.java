package com.javaded78.domain.stat;

public class FloatStatistics extends ShortStatistics {

    private double max = Double.MIN_VALUE;
    private double min = Double.MAX_VALUE;
    private double sum;

    public FloatStatistics(final String fileName) {
        super(fileName);
    }

    public void update(final double value) {
        super.setCount();
        this.max = Math.max(this.max, value);
        this.min = Math.min(this.min, value);
        this.sum += value;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }
    public double getSum() {
        return sum;
    }

    public double getAvg() {
        return super.getCount() == 0 ? 0 : this.sum / super.getCount();
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
