package com.javaded78.broker.data.impl;

import com.javaded78.broker.data.TypeDeterminer;
import com.javaded78.domain.DataType;

import java.util.regex.Pattern;

public class FloatTypeDeterminer implements TypeDeterminer {

    private static final Pattern FLOAT_PATTERN = Pattern.compile("-?\\d+(.\\d+)?([Ee][-+]?\\d+)?");

    @Override
    public DataType determineType(final String value) {
        if (FLOAT_PATTERN.matcher(value).matches()) {
            return DataType.FLOAT;
        }
        return null;
    }
}
