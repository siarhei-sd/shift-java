package com.javaded78.controller;

import com.javaded78.aspect.ExceptionHandler;
import com.javaded78.config.AppConfig;
import com.javaded78.config.Configuration;
import com.javaded78.domain.stat.Statistics;
import com.javaded78.service.FileProcessor;
import com.javaded78.service.Processor;
import com.javaded78.service.sender.ConsoleSender;
import com.javaded78.service.sender.Sender;

import java.util.List;

public class DispatcherController implements Controller {

    private static final Controller INSTANCE = (Controller) ExceptionHandler.createProxy(
            new DispatcherController()
    );

    private final Processor fileProcessor = FileProcessor.getInstance();
    private final Sender consoleSender = ConsoleSender.getInstance();
    private final Configuration config = Configuration.getInstance();

    private DispatcherController() {
    }

    public void dispatch(final String[] args) {
        AppConfig appConfig = config.configure(args);
        List<Statistics> statistics = fileProcessor.process(appConfig);
        consoleSender.send(statistics);
    }

    public static Controller getInstance() {
        return INSTANCE;
    }
}
