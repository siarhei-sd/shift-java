package com.javaded78.producer;

import com.javaded78.broker.Broker;
import com.javaded78.service.reader.Reader;

import java.util.List;

public class Producer implements Runnable {

    private final Broker broker;
    private final Reader reader;
    private final List<String> inputFiles;


    public Producer(final List<String> inputFiles,
                    final Broker broker,
                    final Reader reader) {
        this.broker = broker;
        this.inputFiles = inputFiles;
        this.reader = reader;
    }

    @Override
    public void run() {
        this.reader.readAll(inputFiles)
                .forEach(broker::put);
        broker.finish();
    }
}
