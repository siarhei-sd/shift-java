package com.javaded78.broker.data.impl;

import com.javaded78.broker.data.TypeDeterminer;
import com.javaded78.domain.DataType;

import java.util.ArrayList;
import java.util.List;

public class TopicSelector {

    private static final TopicSelector INSTANCE = new TopicSelector();

    private final List<TypeDeterminer> determiners = new ArrayList<>();

    private TopicSelector() {
        determiners.add(new IntegerTypeDeterminer());
        determiners.add(new FloatTypeDeterminer());
    }

    public DataType pick(final String value) {
        for (TypeDeterminer determiner : determiners) {
            DataType dataType = determiner.determineType(value);
            if (dataType!= null) {
                return dataType;
            }
        }
        return DataType.STRING;
    }

    public static TopicSelector getInstance() {
        return INSTANCE;
    }
}
