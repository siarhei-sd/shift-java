package org.egorsemenovv.statistics;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Statistics<T> {

    private final String fileName;
    private int numberOfElements = 0;
    private T maxElement;
    private T minElement;

    protected Statistics(String fileName){
        this.fileName = fileName;
    }

}
