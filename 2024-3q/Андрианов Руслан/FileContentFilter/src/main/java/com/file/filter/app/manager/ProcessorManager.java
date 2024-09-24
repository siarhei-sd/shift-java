package com.file.filter.app.manager;

import com.file.filter.app.file.FileType;
import com.file.filter.app.file.FileTypeDetector;
import com.file.filter.app.processor.DoubleProcessor;
import com.file.filter.app.processor.IntegersProcessor;
import com.file.filter.app.processor.StringProcessor;

import java.util.List;
import java.util.Set;

public class ProcessorManager {
    private final FilesContentManager optionManager;
    private final StringProcessor stringProcessor;
    private final DoubleProcessor doubleProcessor;
    private final IntegersProcessor integersProcessor;
    private final FileTypeDetector fileTypeDetector;

    public ProcessorManager(FilesContentManager optionManager,
                            StringProcessor stringProcessor,
                            DoubleProcessor doubleProcessor,
                            IntegersProcessor integersProcessor,
                            FileTypeDetector fileTypeDetector) {
        this.optionManager = optionManager;
        this.stringProcessor = stringProcessor;
        this.doubleProcessor = doubleProcessor;
        this.integersProcessor = integersProcessor;
        this.fileTypeDetector = fileTypeDetector;
    }

    public void runProcessor() {
        List<String> filesContent = optionManager.getFilesContent();
        Set<FileType> filesTypes = fileTypeDetector.getFilesTypes(filesContent);
        chooseProcessor(filesTypes);
    }

    private void chooseProcessor(Set<FileType> fileTypes) {
        for (FileType type : fileTypes) {
            switch (type) {
                case INTEGERS -> integersProcessor.start();
                case FLOATS -> doubleProcessor.start();
                case STRINGS -> stringProcessor.start();
                default -> System.out.println("Unknown file type: " + type);
            }
        }
    }
}
