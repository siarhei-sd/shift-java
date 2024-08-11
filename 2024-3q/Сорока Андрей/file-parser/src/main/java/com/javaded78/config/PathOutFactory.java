package com.javaded78.config;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathOutFactory {

    private static final PathOutFactory INSTANCE = new PathOutFactory();

    private PathOutFactory() {
    }

    public Path create(final AppConfig config,
                       final String defaultPath) {
        return Paths.get(config.getPathOut(), getFileName(config.getPrefOut(), defaultPath));
    }

    private String getFileName(final String prefix,
                               final String name) {
        return prefix != null ? prefix + name : name;
    }

    public static PathOutFactory getInstance() {
        return INSTANCE;
    }
}
