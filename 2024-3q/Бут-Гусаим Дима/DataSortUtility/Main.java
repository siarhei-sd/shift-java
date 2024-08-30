import java.util.*;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            CommandLineOptions options = new CommandLineOptions(args);
            List<String> fileNames = options.getFileNames();
            if (fileNames.isEmpty())
            {
                System.out.println("Error: Input files not found.");
                return;
            }
            DataFilter filter = new DataFilter(options);
            for (String fileName : fileNames)
            {
                filter.processFile(fileName);
            }
            filter.writeResults();
            filter.printStatistics();
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
}