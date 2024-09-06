package org.example.core.exeptions;

import java.text.MessageFormat;
import java.util.function.Supplier;

public class KoronaFileHasNotReadException extends RuntimeException {

    public KoronaFileHasNotReadException(final String message) {
        super(message);
    }

    public KoronaFileHasNotReadException(
            final String templateMessage,
            final Object... params
    ) {
        super(MessageFormat.format(templateMessage, params));
    }

    public KoronaFileHasNotReadException(
            final String templateMessage,
            final Throwable e
    ) {
        super(templateMessage, e);
    }

    public static Supplier<KoronaFileHasNotReadException> supplier(
            final String templateMessage,
            final Object... params
    ) {
        return () -> new KoronaFileHasNotReadException(templateMessage, params);
    }
}
