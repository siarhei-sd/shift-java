package org.example.core.exeptions;

import java.text.MessageFormat;
import java.util.function.Supplier;

public class KoronaFileHasNotWrittenException extends RuntimeException {

    public KoronaFileHasNotWrittenException(final String message) {
        super(message);
    }

    public KoronaFileHasNotWrittenException(
            final String templateMessage,
            final Object... params
    ) {
        super(MessageFormat.format(templateMessage, params));
    }

    public KoronaFileHasNotWrittenException(
            final String templateMessage,
            final Throwable e
    ) {
        super(templateMessage, e);
    }

    public static Supplier<KoronaFileHasNotWrittenException> supplier(
            final String templateMessage,
            final Object... params
    ) {
        return () -> new KoronaFileHasNotWrittenException(templateMessage, params);
    }
}
