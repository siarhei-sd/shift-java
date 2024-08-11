package com.javaded78.config;

import com.beust.jcommander.JCommander;

public class Configuration {

    public static final Configuration INSTANCE = new Configuration();

    private Configuration() {
    }

    public AppConfig configure(final String[] args) {
        AppConfig appConfig = new AppConfig();
        JCommander.newBuilder()
                .addObject(appConfig)
                .build()
                .parse(args);
        return appConfig;
    }

    public static Configuration getInstance() {
        return INSTANCE;
    }
}
