package com.file.filter.app;

import com.file.filter.app.factory.ProcessorFactory;
import com.file.filter.app.manager.ProcessorManager;

public class Main {

    public static void main(String[] args) {

        ProcessorFactory processorFactory = new ProcessorFactory(args);
        ProcessorManager processorManager = processorFactory.getProcessorManager();
        processorManager.runProcessor();
    }
}
