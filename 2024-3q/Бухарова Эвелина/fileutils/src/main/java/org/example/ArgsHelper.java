package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArgsHelper {

    public static String getStatisticType(List<String> argsList) {
        String statisticType = null;
        if (argsList.contains("-f")) {
            statisticType = "-f";
        } else if (argsList.contains("-s")) {
            statisticType = "-s";
        }
        return statisticType;
    }

    public static String getOutputPath(List<String> argsList) {
        String outputPath = "../files/";
        int pathIndex = argsList.indexOf("-o");
        if (pathIndex != -1 && pathIndex + 1 < argsList.size()) {
            outputPath = argsList.get(pathIndex + 1);
        } else {
            System.out.println("Wrong or absent parameter -o");
        }
        return outputPath;
    }

    public static String getFilePrefix(List<String> argsList) {
        String prefix = "";
        int prefixIndex = argsList.indexOf("-p");
        if (prefixIndex != -1 && prefixIndex + 1 < argsList.size()) {
            prefix = argsList.get(prefixIndex + 1);
        } else {
            System.out.println("Wrong or absent parameter -p");
        }
        return prefix;
    }

    public static List<String> getFiles(List<String> argsList) {

        return argsList.stream()
                .filter((String arg) -> arg.endsWith(".txt"))
                .collect(Collectors.toList());
    }

    public static boolean getAppend(List<String> argsList) {
        return argsList.contains("-a");
    }
}
