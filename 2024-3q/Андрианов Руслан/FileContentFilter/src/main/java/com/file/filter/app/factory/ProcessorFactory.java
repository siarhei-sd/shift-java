package com.file.filter.app.factory;

import com.file.filter.app.cli.CommandLineOptions;
import com.file.filter.app.file.FileTypeDetector;
import com.file.filter.app.manager.FileManager;
import com.file.filter.app.manager.FilesContentManager;
import com.file.filter.app.manager.ProcessorManager;
import com.file.filter.app.processor.DoubleProcessor;
import com.file.filter.app.processor.IntegersProcessor;
import com.file.filter.app.processor.StringProcessor;

public class ProcessorFactory {
    private final ProcessorManager processorManager;

    public ProcessorFactory(String[] args) {
        CommandLineOptions commandLineOptions = new CommandLineOptions(args);
        FileManager fileManager = new FileManager();
        FilesContentManager optionManager = new FilesContentManager(commandLineOptions, fileManager);
        IntegersProcessor integersProcessor = new IntegersProcessor(optionManager, fileManager, commandLineOptions);
        DoubleProcessor doubleProcessor = new DoubleProcessor(optionManager, fileManager, commandLineOptions);
        StringProcessor stringProcessor = new StringProcessor(optionManager, fileManager, commandLineOptions);
        FileTypeDetector fileTypeDetector = new FileTypeDetector();
        processorManager = new ProcessorManager(optionManager, stringProcessor, doubleProcessor, integersProcessor, fileTypeDetector);
    }

    public ProcessorManager getProcessorManager() {
        return processorManager;
    }
}
