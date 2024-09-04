package com.qooriq.stats;

public abstract class Stats<T> {
    protected T max;
    protected T min;
    protected T avg;
    protected int count = 0;
    protected T sum;

    protected abstract void chckMax(T num);
    protected abstract void chckMin(T num);
    protected abstract void chckAll(T num);
    protected abstract void chngAvg(T num);
    protected abstract void chngSum(T num);
    protected abstract String shortStats();
    protected abstract String fullStats();
    public void chngCount(){
        count++;
    }

}
