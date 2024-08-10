package org.dezzzl;

import lombok.Getter;

@Getter
public enum Option {
    APPEND_TO_THE_END,
    PATH_FOR_RESULT,
    PREFIX_FOR_NAMES,
    SHORT_STATISTICS,
    FULL_STATISTICS;

    public static Option fromStringToOption(String flag) {
        return switch (flag) {
            case "-a" -> Option.APPEND_TO_THE_END;
            case "-o" -> Option.PATH_FOR_RESULT;
            case "-p" -> Option.PREFIX_FOR_NAMES;
            case "-s" -> Option.SHORT_STATISTICS;
            case "-f" -> Option.FULL_STATISTICS;
            default -> throw new RuntimeException("Unknown flag!");
        };
    }
}
