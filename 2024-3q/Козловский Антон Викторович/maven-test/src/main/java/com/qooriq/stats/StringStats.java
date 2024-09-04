package com.qooriq.stats;

public class StringStats extends Stats<Integer>{

    public StringStats() {
        min = Integer.MAX_VALUE;
        max = Integer.MIN_VALUE;
    }

    @Override
    protected void chckMax(Integer num) {
        max = Math.max(max, num);
    }

    @Override
    protected void chckMin(Integer num) {
        min = Math.min(num, min);
    }

    @Override
    public void chckAll(Integer num) {
        chckMin(num);
        chckMax(num);
    }

    @Override
    public void chngAvg(Integer num) {
        return;
    }

    @Override
    protected void chngSum(Integer num) {
        return;
    }

    @Override
    public String shortStats() {
        return "short statistics: elements = " + count;
    }

    @Override
    public String fullStats() {
        return "full statistics: elements = " + count + "; min = " + min + "; max = " + max;
    }
}
