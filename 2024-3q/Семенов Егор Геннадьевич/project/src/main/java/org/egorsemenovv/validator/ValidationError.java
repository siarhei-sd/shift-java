package org.egorsemenovv.validator;

import lombok.Value;

@Value(staticConstructor = "of")
public class ValidationError {

    ErrorType errorType;
    String message;

}
