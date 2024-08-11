package com.javaded78.broker;

import com.javaded78.domain.DataType;

import java.util.concurrent.ArrayBlockingQueue;

public class BrokerFactory {

    private static final BrokerFactory INSTANCE = new BrokerFactory();
    public static final int CAPACITY = 100;
    private final Broker broker = new MessageBroker();

    private BrokerFactory() {
    }

    public Broker createBroker() {
        this.broker.addTopic(new ArrayBlockingQueue<>(CAPACITY), DataType.STRING);
        this.broker.addTopic(new ArrayBlockingQueue<>(CAPACITY), DataType.INTEGER);
        this.broker.addTopic(new ArrayBlockingQueue<>(CAPACITY), DataType.FLOAT);
        return broker;
    }

    public static BrokerFactory getInstance() {
        return INSTANCE;
    }
}
