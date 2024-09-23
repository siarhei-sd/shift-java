import java.util.ArrayList;
import java.util.List;

public class CmdParser {
    private final String[] args;
    private String outputDirectory = ".";
    private String prefix = "";
    private boolean mode = false;
    private boolean fullStats = false;
    private final List<String> inputFiles = new ArrayList<>();

    public CmdParser(String[] args) {
        this.args = args;
        parse();
    }
    // разбор префиксов
    private void parse(){
        for(int i = 0; i < args.length; i++){
            switch (args[i]){
                case "-o":
                    if (i + 1 < args.length){
                        outputDirectory = args[++i];
                    }
                    break;
                case "-p":
                    if (i + 1 < args.length){
                        prefix = args[++i];
                    }
                    break;
                case "-a":
                    mode = true;
                    break;
                case "-s":
                    fullStats = false;
                    break;
                case "-f":
                    fullStats = true;
                    break;
                default:
                    inputFiles.add(args[i]);
                    break;
            }
        }
    }

    public String getOutputDirectory(){
        return outputDirectory;
    }

    public String getPrefix(){
        return prefix;
    }

    public boolean isMode(){
        return mode;
    }

    public boolean isFullStats(){
        return fullStats;
    }

    public boolean isValid(){
        return !inputFiles.isEmpty();
    }

    public List<String> getInputFiles() {
        return inputFiles;
    }
}
