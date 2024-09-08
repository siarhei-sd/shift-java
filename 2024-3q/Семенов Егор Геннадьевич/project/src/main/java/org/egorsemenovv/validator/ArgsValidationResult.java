package org.egorsemenovv.validator;

import lombok.Getter;
import lombok.Setter;
import org.egorsemenovv.statistics.StatisticsType;

import java.util.*;

import static org.egorsemenovv.statistics.StatisticsType.NONE;

@Getter
public class ArgsValidationResult {

    private final List<ValidationError> validationErrors = new ArrayList<>();
    private final Set<String> fileNames = new LinkedHashSet<>();
    @Setter
    private String path = "";
    @Setter
    private String prefix = "";
    @Setter
    private boolean append = false;
    @Setter
    private StatisticsType statisticsType = NONE;

    public void addError(ValidationError validationError) {
        this.validationErrors.add(validationError);
    }

    public boolean isValid() {
        return validationErrors.isEmpty();
    }

    public void addFileName(String fileName) {
        fileNames.add(fileName);
    }
}
