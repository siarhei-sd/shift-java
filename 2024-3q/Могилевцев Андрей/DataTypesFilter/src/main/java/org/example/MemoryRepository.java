package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MemoryRepository {
    static List<Integer> integers;
    static List<String> strings;
    static List<Float> floats;

    public static void addInteger(int var) {
        if (integers == null) integers = new ArrayList<>();
        integers.add(var);
    }

    public static void addString(String var) {
        if(strings == null) strings = new ArrayList<>();
        strings.add(var);
    }

    public static void addFloat(Float var) {
        if(floats == null) floats = new ArrayList<>();
        floats.add(var);
    }

    public static void printAll() {
        System.out.println("!!!!!!!!!!All integers in files!!!!!!!!!!");
        integers.forEach(System.out::println);
        System.out.println("!!!!!!!!!!All strings in files!!!!!!!!!!");
        strings.forEach(System.out::println);
        System.out.println("!!!!!!!!!!All double in files!!!!!!!!!!");
        floats.forEach(System.out::println);
    }

    public static String getIntFullStats() {
        int sum = integers.stream().mapToInt(Integer::intValue).sum();
        double average = integers.stream().mapToDouble(Integer::doubleValue).average().orElse(0.0);
        int min = Collections.min(integers);
        int max = Collections.max(integers);
        String statistic = String.format("integers full statistic: " +
                "elements = %d, min = %d, max = %d, sum = %d, average = %f"
                , integers.size(), min, max, sum, average);
        return statistic;
    }

    public static String getFloatFullStats() {
        double sum = floats.stream().mapToDouble(Float::doubleValue).sum();
        double average = floats.stream().mapToDouble(Float::doubleValue).average().orElse(0.0);
        double min = Collections.min(floats);
        double max = Collections.max(floats);
        String statistic = String.format("double full statistic: " +
                        "elements = %d, min = %f, max = %f, sum = %f, average = %f"
                , floats.size(), min, max, sum, average);
        return statistic;
    }

    public static String getStringFullStats() {
        int shortLength = strings.stream().min(Comparator.comparingInt(String::length)).get().length();
        int longLength = strings.stream().max(Comparator.comparingInt(String::length)).get().length();
        String statistic = String.format("strings full statistic: " +
                        "elements = %d, shortest = %d, longest = %d"
                , strings.size(), shortLength, longLength);
        return statistic;
    }


    public static void printShortStatistics() {
        System.out.println(Parameters.getPrefix() + "integers.txt short statistic: elements = " + integers.size());
        System.out.println(Parameters.getPrefix() + "strings.txt short statistic: elements = " + strings.size());
        System.out.println(Parameters.getPrefix() + "floats.txt short statistic: elements = " + floats.size());
    }

    public static void printFullStatistics() {
        System.out.println(getIntFullStats());
        System.out.println(getStringFullStats());
        System.out.println(getFloatFullStats());
    }

    public static void saveResults() {
        if(!strings.isEmpty()) {
            try (FileWriter writer = new FileWriter(Parameters.getDestinationfolder() + Parameters.getPrefix() + "strings.txt", Parameters.isAddToFile())) {
                for (String data : strings) {
                    writer.write(data);
                    writer.append('\n');
                }
                writer.flush();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        if(!integers.isEmpty()) {
            try (FileWriter writerInt = new FileWriter(Parameters.getDestinationfolder() + Parameters.getPrefix() + "integers.txt", Parameters.isAddToFile())) {
                for (Integer data : integers) {
                    writerInt.write(data.toString());
                    writerInt.append('\n');
                }
                writerInt.flush();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

        if(!floats.isEmpty()) {
            try (FileWriter writerFloat = new FileWriter(Parameters.getDestinationfolder() + Parameters.getPrefix() + "floats.txt", Parameters.isAddToFile())) {
                for (Float data : floats) {
                    writerFloat.write(data.toString());
                    writerFloat.append('\n');
                }
                writerFloat.flush();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }


}
