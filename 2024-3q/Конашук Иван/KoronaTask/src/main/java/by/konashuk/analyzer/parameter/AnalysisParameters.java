package by.konashuk.analyzer.parameter;
import java.util.Set;

public final class AnalysisParameters {
    private final String path;
    private final String prefix;
    private final Boolean isShortStatistic;
    private final Boolean isAppend;
    private final Boolean isFullStatistic;
    private final Set<String> fileNames;

    AnalysisParameters(String path,
                       String prefix,
                       Boolean isAppend,
                       Boolean isShortStatistic,
                       Boolean isFullStatistic,
                       Set<String> fileNames
    ) {
        this.path = path;
        this.prefix = prefix;
        this.isAppend = isAppend;
        this.isShortStatistic = isShortStatistic;
        this.isFullStatistic = isFullStatistic;
        this.fileNames = fileNames;
    }

    public String getPath() {
        return this.path;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public Boolean getShortStatistic() {
        return this.isShortStatistic;
    }

    public Boolean getIsAppend() {
        return this.isAppend;
    }

    public Boolean getFullStatistic() {
        return this.isFullStatistic;
    }

    public Set<String> getFileNames() {
        return this.fileNames;
    }
}
