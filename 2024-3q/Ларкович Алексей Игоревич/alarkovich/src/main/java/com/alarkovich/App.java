package com.alarkovich;

import java.io.*;
import java.util.*;

public class App {

    private static final String TYPE_NAME_INT       = "int";
    private static final String TYPE_NAME_FLOAT     = "float";
    private static final String TYPE_NAME_STRING    = "string";

    private static Map<String, DataHandler> handlersMap;;

    private static Map<String, ArrayList<String>> dataMap = Map.of(
        TYPE_NAME_INT, new ArrayList<String>(),
        TYPE_NAME_FLOAT, new ArrayList<String>(),
        TYPE_NAME_STRING, new ArrayList<String>()
    );

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.err.println("No arguments!");
            return;
        }

        Params params = parseArguments(args);

        if (params.files.size() == 0) {
            System.err.println("No files. Please add some!");
            return;
        }

        readFiles(params.files);

        initHandlersMap(params.path, params.prefix, params.append);

        writeOutputs();

        writeStats(params.statsFull, params.statsShort);

        System.out.println("Success.");
    }

    private static void writeStats(Boolean statsFull, Boolean statsShort) {
        handlersMap.get(TYPE_NAME_INT).writeStats(statsFull, statsShort);
        handlersMap.get(TYPE_NAME_FLOAT).writeStats(statsFull, statsShort);
        handlersMap.get(TYPE_NAME_STRING).writeStats(statsFull, statsShort);
    }

    private static void writeOutputs() {
        handlersMap.get(TYPE_NAME_INT).setData(dataMap.get(TYPE_NAME_INT)).writeToFile();
        handlersMap.get(TYPE_NAME_FLOAT).setData(dataMap.get(TYPE_NAME_FLOAT)).writeToFile();
        handlersMap.get(TYPE_NAME_STRING).setData(dataMap.get(TYPE_NAME_STRING)).writeToFile();
    }

    private static void initHandlersMap(String path, String prefix, Boolean append) {
        handlersMap = Map.of(
            TYPE_NAME_INT, (DataHandler)new IntHandler(path, prefix, append),
            TYPE_NAME_FLOAT, (DataHandler)new FloatHandler(path, prefix, append),
            TYPE_NAME_STRING, (DataHandler)new StringHandler(path, prefix, append)
        );
    }

    private static void readFiles(ArrayList<String> files) {
        for (int i = 0; i < files.size(); i++) {
            try (Scanner fileScanner = new Scanner(new File(".", files.get(i)), "UTF-8")) {

                // Scanner fileScanner = new Scanner(new BufferedReader(new FileReader("." + files.get(i))));
                while(fileScanner.hasNextLine()) {
                    Scanner lineScanner = new Scanner(fileScanner.nextLine());
                    ArrayList<String> strings = new ArrayList<String>();

                    while(lineScanner.hasNext()) {
                        String item = lineScanner.next();

                        if (item.matches("-?\\d+")) {
                            dataMap.get(TYPE_NAME_INT).add(item);
                        } else if (item.matches("-?\\d+\\.\\d+(?:E[+-]?\\d+)?")) {
                            dataMap.get(TYPE_NAME_FLOAT).add(item);
                        } else {
                            strings.add(item);
                        }
                    }

                    if (strings.size() > 0) {
                        dataMap.get(TYPE_NAME_STRING).add(String.join(" ", strings));
                    }

                    lineScanner.close();
                }

                fileScanner.close();
            } catch (FileNotFoundException e) {
                System.err.println("File Read error: " + files.get(i));
                e.printStackTrace();
            }
        }

    }

    private static Params parseArguments(String[] args) {
        Params params = new Params();

        for (Integer i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o":
                    if (i + 1 < args.length) {
                        params.path = args[++i];
                    }
                    break;
                case "-p":
                    if (i + 1 < args.length) {
                        params.prefix = args[++i];
                    }
                    break;
                case "-a":
                    params.append = true;
                    break;
                case "-s":
                    params.statsShort = true;
                    break;
                case "-f":
                    params.statsFull = true;
                    break;
                default:
                    params.files.add(args[i]);
                    break;
            }
        }
        return params;
    }

    private static class Params {
        ArrayList<String> files = new ArrayList<>();

        String path             = ".";
        String prefix           = "";

        Boolean append          = false;
        Boolean statsFull       = false;
        Boolean statsShort      = false;
    }
}
