package com.qooriq.stats;

public class FloatStats extends Stats<Double>{

    public FloatStats() {
        min = Double.POSITIVE_INFINITY;
        max = Double.NEGATIVE_INFINITY;
        sum = 0d;
        avg = 0d;
    }

    @Override
    protected void chckMax(Double num) {
        max = Math.max(max, num);
    }

    @Override
    protected void chckMin(Double num) {
        min = Math.min(min, num);
    }

    @Override
    public void chckAll(Double num) {
        chckMax(num);
        chckMin(num);
    }

    @Override
    public void chngAvg(Double num) {
        chngCount();
        chngSum(num);
        avg = sum / count;
    }

    @Override
    protected void chngSum(Double num) {
        sum += num;
    }

    @Override
    public String shortStats() {
        return "short statistics: elements = " + count;
    }

    @Override
    public String fullStats() {
        return "full statistics: elements = " + count + "; min = " + min + "; max = " + max + "; sum = " + sum + "; average = " + avg;
    }
}
