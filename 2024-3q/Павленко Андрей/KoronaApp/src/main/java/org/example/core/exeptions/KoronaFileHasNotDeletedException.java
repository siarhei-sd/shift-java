package org.example.core.exeptions;

import java.text.MessageFormat;
import java.util.function.Supplier;

public class KoronaFileHasNotDeletedException extends RuntimeException {

    public KoronaFileHasNotDeletedException(final String message) {
        super(message);
    }

    public KoronaFileHasNotDeletedException(
            final String templateMessage,
            final Object... params
    ) {
        super(MessageFormat.format(templateMessage, params));
    }

    public KoronaFileHasNotDeletedException(
            final String templateMessage,
            final Throwable e
    ) {
        super(templateMessage, e);
    }

    public static Supplier<KoronaFileHasNotDeletedException> supplier(
            final String templateMessage,
            final Object... params
    ) {
        return () -> new KoronaFileHasNotDeletedException(templateMessage, params);
    }
}
