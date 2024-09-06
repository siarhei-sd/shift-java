package org.example.core.exeptions;

import java.text.MessageFormat;
import java.util.function.Supplier;

public class KoronaArgsNotFoundException extends RuntimeException {

    public KoronaArgsNotFoundException(final String message) {
        super(message);
    }

    public KoronaArgsNotFoundException(
            final String templateMessage,
            final Object... params
    ) {
        super(MessageFormat.format(templateMessage, params));
    }

    public static Supplier<KoronaArgsNotFoundException> supplier(
            final String templateMessage,
            final Object... params
    ) {
        return () -> new KoronaArgsNotFoundException(templateMessage, params);
    }
}
