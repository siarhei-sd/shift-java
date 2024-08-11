package com.javaded78.broker;

import com.javaded78.broker.data.impl.TopicSelector;
import com.javaded78.domain.DataType;
import com.javaded78.exception.ProcessFileException;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class MessageBroker implements Broker {

    private final TopicSelector selector = TopicSelector.getInstance();

    private final Map<DataType, BlockingQueue<String>> topics;

    public MessageBroker() {
        this.topics = new EnumMap<>(DataType.class);
    }

    @Override
    public void addTopic(final BlockingQueue<String> topic,
                         final DataType name) {
        this.topics.put(name, topic);
    }

    @Override
    public void deleteTopic(final DataType name) {
        this.topics.remove(name);
    }

    @Override
    public Optional<BlockingQueue<String>> getTopic(final DataType name) {
        return Optional.ofNullable(this.topics.get(name));
    }

    @Override
    public BlockingQueue<String> selectTopic(final String message) {
        DataType topicName = selector.pick(message);
        return this.topics.get(topicName);
    }

    @Override
    public void put(final String message) {
        try {
            this.selectTopic(message).put(message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ProcessFileException("Thread was interrupted while putting message: " + message);
        }
    }

    @Override
    public String take(DataType name) {
        try {

            return this.topics.get(name).poll(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ProcessFileException("Thread was interrupted while taken message");
        }
    }

    @Override
    public void finish() {
        this.topics.values().forEach(topic -> {
            try {
                topic.put(EOF);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new ProcessFileException(e.getMessage());
            }
        });
    }
}
