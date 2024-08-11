package com.javaded78.broker.data;

import com.javaded78.domain.DataType;

public interface TypeDeterminer {

    DataType determineType(String value);
}
