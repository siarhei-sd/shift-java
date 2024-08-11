package com.javaded78.broker;

import com.javaded78.domain.DataType;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;

public interface Broker {

    String EOF = "END_OF_FILE_SIGNAL";

    void addTopic(BlockingQueue<String> topic,
                  DataType name);

    void deleteTopic(DataType name);

    Optional<BlockingQueue<String>> getTopic(DataType name);

    BlockingQueue<String> selectTopic(String message);

    void put(String message);

    String take(DataType name);

    void finish();
}
