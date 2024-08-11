package com.javaded78.broker.data.impl;

import com.javaded78.broker.data.TypeDeterminer;
import com.javaded78.domain.DataType;

import java.util.regex.Pattern;

public class IntegerTypeDeterminer implements TypeDeterminer {

    private static final Pattern INTEGER_PATTERN = Pattern.compile("-?\\d+");

    @Override
    public DataType determineType(final String value) {
        if (INTEGER_PATTERN.matcher(value).matches()) {
            return DataType.INTEGER;
        }
        return null;
    }
}
