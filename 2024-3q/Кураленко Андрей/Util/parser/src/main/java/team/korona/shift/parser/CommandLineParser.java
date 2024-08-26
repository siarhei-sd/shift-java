package team.korona.shift.parser;

import java.util.List;

public interface CommandLineParser {

    boolean isAppendMode();

    String getOutputPath();

    String getPrefix();

    List<String> getInputFiles();

    StatisticsType getStatisticsType();
}
