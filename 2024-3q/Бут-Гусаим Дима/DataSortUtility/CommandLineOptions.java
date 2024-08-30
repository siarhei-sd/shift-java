import java.util.*;

public class CommandLineOptions
{
    private List<String> fileNames;
    private String outputDirectory;
    private String filePrefix;
    private boolean append;
    private boolean fullStats;

    public CommandLineOptions(String[] args)
    {
        fileNames = new ArrayList<>();
        outputDirectory = ".";
        filePrefix = "";
        append = false;
        fullStats = false;
        parseArgs(args);
    }

    private void parseArgs(String[] args)
    {
        for (int i = 0; i < args.length; i++)
        {
            switch (args[i])
            {
                case "-o":
                    outputDirectory = args[++i];
                    break;
                case "-p":
                    filePrefix = args[++i];
                    break;
                case "-a":
                    append = true;
                    break;
                case "-s":
                    fullStats = false;
                    break;
                case "-f":
                    fullStats = true;
                    break;
                default:
                    fileNames.add(args[i]);
                    break;
            }
        }
    }

    public List<String> getFileNames()
    {
        return fileNames;
    }

    public String getOutputDirectory()
    {
        return outputDirectory;
    }

    public String getFilePrefix()
    {
        return filePrefix;
    }

    public boolean isAppend()
    {
        return append;
    }

    public boolean isFullStats()
    {
        return fullStats;
    }
}
