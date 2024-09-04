package com.qooriq.stats;

public class IntegerStats extends Stats<Integer> {

    public IntegerStats() {
        max = Integer.MIN_VALUE;
        min = Integer.MAX_VALUE;
        sum = 0;
        avg = 0;
    }

    @Override
    protected void chckMax(Integer num) {
        max = Math.max(max, num);
    }

    @Override
    protected void chckMin(Integer num) {
        min = Math.min(min, num);
    }

    @Override
    public void chckAll(Integer num) {
        chckMax(num);
        chckMin(num);
    }

    @Override
    public void chngAvg(Integer num) {
        chngCount();
        chngSum(num);
        avg = sum / count;
    }

    @Override
    protected void chngSum(Integer num) {
        sum = sum + num;
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
