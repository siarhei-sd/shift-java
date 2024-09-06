package org.example.core.exeptions;

import java.text.MessageFormat;
import java.util.function.Supplier;

public class KoronaIncorrectCommandLineArgumentsException extends RuntimeException {

    public KoronaIncorrectCommandLineArgumentsException(final String message) {
        super(message);
    }

    public KoronaIncorrectCommandLineArgumentsException(
            final String templateMessage,
            final Object... params
    ) {
        super(MessageFormat.format(templateMessage, params));
    }

    public static Supplier<KoronaIncorrectCommandLineArgumentsException> supplier(
            final String templateMessage,
            final Object... params
    ) {
        return () -> new KoronaIncorrectCommandLineArgumentsException(templateMessage, params);
    }
}
