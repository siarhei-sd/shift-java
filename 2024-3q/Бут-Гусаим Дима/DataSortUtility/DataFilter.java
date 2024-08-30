import java.io.*;
import java.util.*;

public class DataFilter
{
    private final CommandLineOptions options;
    private final List<Integer> integers;
    private final List<Float> floats;
    private final List<String> strings;

    public DataFilter(CommandLineOptions options)
    {
        this.options = options;
        this.integers = new ArrayList<>();
        this.floats = new ArrayList<>();
        this.strings = new ArrayList<>();
    }

    public void processFile(String fileName)
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                parseLine(line);
            }
        }
        catch (IOException e) {
            System.out.println("Error processing file " + fileName + ": " + e.getMessage());
        }
    }

    private void parseLine(String line)
    {
        try
        {
            if (line.contains("."))
            {
                floats.add(Float.parseFloat(line));
            }
            else
            {
                integers.add(Integer.parseInt(line));
            }
        }
        catch (NumberFormatException e)
        {
            strings.add(line);
        }
    }

    public void writeResults()
    {
        writeToFile("integers.txt", integers);
        writeToFile("floats.txt", floats);
        writeToFile("strings.txt", strings);
    }

    private void writeToFile(String baseName, List<?> data)
    {
        if (data.isEmpty())
        {
            return;
        }
        String fileName = options.getOutputDirectory() + File.separator + options.getFilePrefix() + baseName;
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, options.isAppend())))
        {
            for (Object item : data)
            {
                writer.println(item.toString());
            }
        }
        catch (IOException e)
        {
            System.out.println("Error writing to file \" + fileName + \": \" + e.getMessage()");
        }
    }

    public void printStatistics()
    {
        printStat("Integers", integers);
        printStat("Floats", floats);
        printStat("Strings", strings);
    }

    @SuppressWarnings("unchecked")

    private void printStat(String type, List<?> data)
    {
        System.out.println(type + " count: " + data.size());
        if (options.isFullStats()) {
            if (data.isEmpty()) {
                return;
            }
            if (data.get(0) instanceof String) {
                List<String> strings = (List<String>) data;
                int minLength = strings.stream().mapToInt(String::length).min().orElse(0);
                int maxLength = strings.stream().mapToInt(String::length).max().orElse(0);
                System.out.println("Shortest: " + minLength + " characters");
                System.out.println("Longest: " + maxLength + " characters");
            } else if (data.get(0) instanceof Integer) {
                List<Integer> ints = (List<Integer>) data;
                int min = ints.stream().min(Integer::compare).orElse(0);
                int max = ints.stream().max(Integer::compare).orElse(0);
                int sum = ints.stream().mapToInt(Integer::intValue).sum();
                int avg = sum / ints.size();
                System.out.println("Min: " + min);
                System.out.println("Max: " + max);
                System.out.println("Sum: " + sum);
                System.out.println("Average: " + avg);
            } else if (data.get(0) instanceof Float) {
                List<Float> floats = (List<Float>) data;
                float min = floats.stream().min(Float::compare).orElse(0.0f);
                float max = floats.stream().max(Float::compare).orElse(0.0f);
                double sum = floats.stream().mapToDouble(Float::doubleValue).sum();
                double avg = sum / floats.size();
                System.out.println("Min: " + min);
                System.out.println("Max: " + max);
                System.out.println("Sum: " + sum);
                System.out.println("Average: " + avg);
            }
        }
    }
}
