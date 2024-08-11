package com.javaded78.producer;

import com.javaded78.broker.Broker;
import com.javaded78.broker.BrokerFactory;
import com.javaded78.config.AppConfig;
import com.javaded78.service.reader.Reader;
import com.javaded78.service.reader.impl.FileReader;

public class ProducerFactory {

    private static final ProducerFactory INSTANCE = new ProducerFactory();
    private final BrokerFactory brokerFactory = BrokerFactory.getInstance();
    private final Reader reader = FileReader.getInstance();

    private ProducerFactory() {}

    public Producer create(final AppConfig config) {
        Broker broker = brokerFactory.createBroker();
        return new Producer(config.getFiles(), broker, reader);
    }

    public static ProducerFactory getInstance() {
        return INSTANCE;
    }
}
