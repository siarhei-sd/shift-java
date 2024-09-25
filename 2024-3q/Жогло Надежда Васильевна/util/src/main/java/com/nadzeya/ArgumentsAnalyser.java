package com.nadzeya;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArgumentsAnalyser {
    private static final String BANNED_CHARS = "[\\\\/:*?\"<>|+.]+";

    Scanner scanner = new Scanner(System.in);
    private final List<String> options = new ArrayList<>();
    private final List<String> files = new ArrayList<>();
    private String namePrefix = "";
    private String path = "";
    private String statisticType = "";
    private boolean rewriteExisting = true;

    public String getPath() {
        return path;
    }

    public String getStatisticType() {
        return statisticType;
    }

    public String getNamePrefix() {
        return namePrefix;
    }

    public List<String> getFiles() {
        return files;
    }

    boolean shouldAppend() {
        return !rewriteExisting;
    }

    public void analyseArgs(String[] args) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-p":
                    options.add(args[i]);
                    if ((i + 1 < args.length)) {
                        checkNamePrefix(args[i + 1]);
                        if (namePrefix.equals(args[i + 1])) i++;
                    }
                    break;
                case "-o":
                    options.add(args[i]);
                    if ((i + 1 < args.length)) {
                        checkPath(args[i + 1]);
                        if (path.equals(args[i + 1])) i++;
                    }
                    break;
                case "-a":
                    options.add(args[i]);
                    rewriteExisting = false;
                    if (i + 1 < args.length && isNextValid(args[i + 1])) {
                        i++;
                    }
                    break;
                case "-s":
                    options.add(args[i]);
                    statisticType = "-s";
                    if ((i + 1 < args.length))
                        while (isNextValid(args[i + 1])) {
                            i++;
                            if ((i + 1 < args.length)) break;
                        }
                    break;
                case "-f":
                    options.add(args[i]);
                    statisticType = "-f";
                    if ((i + 1 < args.length))
                        while (isNextValid(args[i + 1])) {
                            i++;
                            if ((i + 1 < args.length)) break;
                        }
                default:
                    if (isValidFileName(args[i])) files.add(args[i]);
            }
        }
        checkArgsForCorrectInput();
    }

    void checkArgsForCorrectInput() {
        if (options.contains(Options.OUTPUT.getOption())) {
            if (path.isEmpty()) requestPathEmpty();
        }
        if (options.contains(Options.PREFIX.getOption())) {
            if (namePrefix.isEmpty()) requestPrefixEmpty();
        }
        if (statisticType.isEmpty()) requestStatistics();

        if (files.isEmpty()) requestFiles();

    }

    void requestPrefixEmpty() {
        System.out.println("Введите префикс для имени новых файлов: ");
        namePrefix = scanner.nextLine();
    }


    void requestPathEmpty() {
        System.out.println("Введите путь для сохранения новых файлов: ");
        path = scanner.nextLine();
    }


    void requestStatistics() {
        System.out.println("Введите тип необходимой статистики (-f/-s): ");
        statisticType = scanner.nextLine();
        while (!(statisticType.equals("-f") || statisticType.equals("-s"))) {
            System.out.println("Неверный ввод. Введите тип необходимой статистики (-f/-s) либо нажмите 0 для закрытия программы: ");
            statisticType = scanner.nextLine();
            if (statisticType.equals("0")) System.exit(-1);
        }
    }

    void requestFiles() {
        System.out.println("Введите имя файла/файлов через пробел, из которых необходимо производить чтение данных, либо нажмите 0 для закрытия программы: ");
        String s = scanner.nextLine();
        if (s.equals("0")) System.exit(-3);
        for (String file : s.split(" ")) {
            if (!isValidFileName(file)) {
                System.out.println(file + "  не включен в список просматриваемых файлов");
            } else files.add(file);
        }
    }

    void checkNamePrefix(String s) {
        namePrefix = s;
        while (namePrefix.matches(".*" + BANNED_CHARS + ".*")) {
            requestPrefix();
        }
    }

    void requestPrefix() {
        System.out.println(namePrefix + " не может быть использован в качестве префикса.");
        System.out.println("Введите префикс имени файла (нельзя использовать \\, /, :, *, ?, \", <, >, |, +, .) либо нажмите 0 для закрытия программы");
        namePrefix = scanner.nextLine();
        if (namePrefix.equals("0")) System.exit(-2);
    }

    void checkPath(String s) {
        path = s;
        while (!path.matches("^/(.*+/?)+$")) {
            requestPath();
        }
    }

    void requestPath() {
        System.out.println(path + " не может быть использован в качестве пути к файлам");
        System.out.println("Введите путь к файлам (например /some/path) либо нажмите 0 для закрытия программы: ");
        path = scanner.nextLine();
        if (path.equals("0")) System.exit(-2);
    }

    boolean isNextValid(String arg) {
        if (arg.charAt(0) != '-' && !arg.matches(".[a-zA-Z0-9]$")) {
            System.out.println(arg + " не является верным аргументом.");
            System.out.println("Символы \"" + arg + "\" игнорируются");
            return true;
        }
        return false;
    }

    boolean isValidFileName(String s) {
        if (!s.matches("^.*\\.[a-zA-Z0-9]{1,5}$")) {
            System.out.println("Неверное имя файла: " + s);
            return false;
        }
        return true;
    }


}
