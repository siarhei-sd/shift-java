package org.egorsemenovv.validator;

import org.egorsemenovv.console.ConsoleArgs;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.egorsemenovv.statistics.StatisticsType.LONG;
import static org.egorsemenovv.statistics.StatisticsType.SHORT;
import static org.egorsemenovv.validator.ErrorType.*;

public class ConsoleArgsValidator{

    private static final ConsoleArgsValidator INSTANCE = new ConsoleArgsValidator();
    private static final String[] INVALID_CHARACTERS = {"<", ">", "\"", "\\", "/", "|", "?", "*"};

    private ConsoleArgsValidator() {}

    public ArgsValidationResult validate(ConsoleArgs args) {
        ArgsValidationResult validationResult = new ArgsValidationResult();

        validateFileNames(args, validationResult);
        validatePath(args, validationResult);
        validatePrefix(args, validationResult);

        if (args.hasOption("-s")){
            validationResult.setStatisticsType(SHORT);
        }
        if (args.hasOption("-f")){
            validationResult.setStatisticsType(LONG);
        }
        if (args.hasOption("-a")){
            validationResult.setAppend(true);
        }

        return validationResult;
    }

    private void validateFileNames(ConsoleArgs args, ArgsValidationResult validationResult) {
        for (String fileName : args.getFileNames()) {
            if (fileName != null && !fileName.isEmpty() && fileName.length() < 256) {
                if (!Files.exists(Path.of(fileName))) {
                    validationResult.addError(ValidationError.of(INVALID_FILE_NAME,
                            fileName + " does not exist"));
                } else {
                    validationResult.addFileName(fileName);
                }
            }
        }
    }

    private void validatePath(ConsoleArgs args, ArgsValidationResult validationResult) {
        String path = args.getPath();
        if (path.length() > 1 && path.startsWith("/")){
            path = path.substring(1);
        }
        if (!Files.exists(Path.of(path)) || path .equals("/")) {
            validationResult.addError(ValidationError.of(INVALID_FOLDER,
                    "folder " + args.getPath() + " does not exist"));
            validationResult.setPath("");
        }else {
            validationResult.setPath(path);
        }
    }

    private void validatePrefix(ConsoleArgs args, ArgsValidationResult validationResult) {
        validationResult.setPrefix(args.getPrefix());
        for (String invalidCharacter : INVALID_CHARACTERS) {
            if (args.getPrefix().contains(invalidCharacter)) {
                validationResult.addError(ValidationError.of(INVALID_PREFIX_NAME,
                        "prefix name can't contains: " + invalidCharacter));
                validationResult.setPrefix("");
                break;
            }
        }
    }

    public static ConsoleArgsValidator getInstance() {
        return INSTANCE;
    }
}
