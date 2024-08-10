package org.dezzzl.statistics;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class Statistics<T> {
    private String pathToFile;

    public abstract void updateStatistics(T value);

    public abstract void printStatistics();
}
