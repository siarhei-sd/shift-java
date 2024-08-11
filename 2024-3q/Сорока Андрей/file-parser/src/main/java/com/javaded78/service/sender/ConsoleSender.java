package com.javaded78.service.sender;

import com.javaded78.domain.stat.Statistics;

import java.util.List;

public class ConsoleSender implements Sender {

    private static final ConsoleSender INSTANCE = new ConsoleSender();

    private ConsoleSender() {
    }

    @Override
    public void send(final List<Statistics> statistics) {
        statistics.forEach(stat -> System.out.println(stat.display()));
    }

    public static ConsoleSender getInstance() {
        return INSTANCE;
    }
}
