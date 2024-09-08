package org.egorsemenovv.statistics;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NumberStatistics<T extends Number> extends Statistics<T> {

    private double average;

    private T sum;

    protected NumberStatistics(String fileName) {
        super(fileName);
    }

}
