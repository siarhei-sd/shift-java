package by.konashuk.analyzer.parameter.constants;

import java.util.Set;

public final class ParameterConstants {
    private ParameterConstants() {
    }

    public static final String PREFIX_PARAM = "-p";
    public static final String PATH_PARAM = "-o";
    public static final String SHORT_STATISTIC_PARAM = "-s";
    public static final String FULL_STATISTIC_PARAM = "-f";
    public static final String APPEND_PARAM = "-a";
    public static final Integer MIN_PREFIX_LENGTH = 3;
    public static final Integer INCREMENT_VALUE = 1;
    public static final String PREFIX_STR = "Prefix";
    public static final String PATH_STR = "Path";

    public static Set<String> PARAMS_VALUES = Set.of(
            PREFIX_PARAM,
            PATH_PARAM,
            SHORT_STATISTIC_PARAM,
            FULL_STATISTIC_PARAM,
            APPEND_PARAM
    );

}
