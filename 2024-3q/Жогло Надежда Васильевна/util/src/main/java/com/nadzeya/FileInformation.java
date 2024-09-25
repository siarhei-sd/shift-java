package com.nadzeya;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileInformation {
    ArgumentsAnalyser argumentsAnalyser;
    String statisticType;
    Map<String, Statistic> typeStatisticMap = new HashMap<>();
    Map<String, String> typeFileNameMap = new HashMap<>();


    {
        typeFileNameMap.put("int", "integers.txt");
        typeFileNameMap.put("float", "floats.txt");
        typeFileNameMap.put("string", "strings.txt");
    }

    boolean append;
    String path;
    List<String> dataFiles;

    public FileInformation(ArgumentsAnalyser argumentsAnalyser) {
        this.argumentsAnalyser = argumentsAnalyser;
        this.dataFiles = argumentsAnalyser.getFiles();
        this.path = argumentsAnalyser.getPath();
        if (!this.path.isEmpty()) {
            createFilePath();
        }
        this.statisticType = argumentsAnalyser.getStatisticType();
        this.append = argumentsAnalyser.shouldAppend();
        createStatisticType();
        if (!argumentsAnalyser.getNamePrefix().isEmpty()) {
            createFileNames();
        }

    }

    private void createFilePath() {
        File directory = new File("./" + path);
        if (!directory.mkdirs() && !directory.exists())
            System.out.println("Произошла ошибка при создании папки " + path);
    }

    private void createStatisticType() {
        if (statisticType.equals("-s")) {
            typeStatisticMap.put("int", new ShortStatistic());
            typeStatisticMap.put("float", new ShortStatistic());
            typeStatisticMap.put("string", new ShortStatistic());
        } else {
            typeStatisticMap.put("int", new FullStatistic());
            typeStatisticMap.put("float", new FullStatistic());
            typeStatisticMap.put("string", new FullStatistic());
        }
    }

    private void createFileNames() {
        String prefix = argumentsAnalyser.getNamePrefix();
        typeFileNameMap.replaceAll((k, v) -> prefix + typeFileNameMap.get(k));
    }

    public void analyseFiles() {
        for (String file : dataFiles)
            analyseFile(file);
    }

    private void analyseFile(String file) {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
            String str;
            while ((str = fileReader.readLine()) != null) {
                processLine(str);
            }

        } catch (IOException e) {
            System.out.println("К сожалению, файл " + file + " не найден.");
        }

    }

    private void processLine(String str) throws IOException {
        try {
            String type = checkType(str);
            addToResultFile(str, typeFileNameMap.get(type));
            typeStatisticMap.get(type).updateStatistics(str, type);
        } catch (Exception e) {
            System.out.println("Извините, произошла ошибка при проверки типов строк");
            System.exit(-3);
        }
    }

    private String checkType(String s) {
        if (s.matches("^-?\\d+$"))
            return "int";
        if (s.matches("^-?\\d+(?:\\.\\d+)?(?:[eE][-+]?\\d+)?$"))
            return "float";
        if (s.contains(" ")) {
            try {
                String[] arr = s.split(" ");
                for (String string : arr) {
                    processLine(string);
                }
            } catch (Exception e) {
                System.out.println("Извините, произошла ошибка при проверки типов строк");
                return "none";
            }
        }
        return "string";
    }

    private void addToResultFile(String s, String resultFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./" + path + "/" + resultFile, append))) {
            writer.write(s + "\n");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            append = true;
        }

    }

    public void outputResult() {
        for (String key : typeStatisticMap.keySet()) {
            if (!typeStatisticMap.get(key).getTypeForStatistics().equals("none")) {
                System.out.print(typeFileNameMap.get(key));
                typeStatisticMap.get(key).printStatistic();
            }

        }
    }
}
