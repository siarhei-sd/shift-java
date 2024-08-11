package com.testtask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import main.java.com.testtask.StatisticsType;

public class Main {

  private static final List<String> filesToCheck = new ArrayList<>();
  private static final String[] filesToWrite = new String[3];
  private static boolean optionA = false;
  private static String mainFilePath = null;
  private static String prefix = null;
  private static StatisticsType statisticsType = StatisticsType.NULL;

  public static void main(String[] args) {
    //Обрабатываем аргументы
    if (!processArguments(args)) {
      return;
    }

    //Подготавливаем файлы для записи
    procedureToPrepareFiles();

    //Сортируем элементы у каждого из полученного файла
    for (String file : filesToCheck) {
      try {
        sortFileElements(file);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    //Смотрим статистику
    if (statisticsType != StatisticsType.NULL) {
      showStatistics();
    }
  }

  /**
   * This function scans elements that we have in files that contain different data types
   * and gives us specific information for our statistic
   */
  public static void showStatistics() {
    if (statisticsType == StatisticsType.FULL) {
      System.out.println("FULL STATISTICS ==>");
    } else {
      System.out.println("SHORT STATISTICS ==>");
    }
    for (int i = 0; i < 3; i++) {
      if (new File(filesToWrite[i]).exists()) {
        System.out.println("\n" + filesToWrite[i] + ":");

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filesToWrite[i]))) {
          List<String> stringList = bufferedReader.lines().toList();
          System.out.println("elements: " + stringList.size());

          if (statisticsType == StatisticsType.FULL && i == 0) {
            System.out.print("max: ");
            stringList.stream().map(Integer::parseInt).max(Integer::compare)
                .ifPresent(System.out::println);

            System.out.print("min: ");
            stringList.stream().map(Integer::parseInt).min(Integer::compare)
                .ifPresent(System.out::println);

            System.out.println("sum: " +
                stringList.stream().map(Integer::parseInt).mapToInt(Integer::valueOf).sum());

            System.out.print("average: ");
            stringList.stream().map(Integer::parseInt).mapToInt(Integer::valueOf).average()
                .ifPresent(System.out::println);
          } else if (statisticsType == StatisticsType.FULL && i == 1) {

            System.out.print("max: ");
            stringList.stream().map(Float::parseFloat).max(Float::compare)
                .ifPresent(System.out::println);

            System.out.print("min: ");
            stringList.stream().map(Float::parseFloat).min(Float::compare)
                .ifPresent(System.out::println);

            System.out.println("sum: " +
                stringList.stream().map(Double::parseDouble).mapToDouble(Double::valueOf).sum());

            System.out.print("average: ");
            stringList.stream().map(Float::parseFloat).mapToDouble(Float::valueOf).average()
                .ifPresent(System.out::println);

          } else if (statisticsType == StatisticsType.FULL) {

            System.out.print("max: ");
            stringList.stream().map(String::length).max(Integer::compareTo)
                .ifPresent(System.out::println);

            System.out.print("min: ");
            stringList.stream().map(String::length).min(Integer::compareTo)
                .ifPresent(System.out::println);

          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * This method builds names of the files that will contain different data types after sorting
   */
  public static void procedureToPrepareFiles() {
    StringBuilder filepath = new StringBuilder();
    if (mainFilePath != null) {
      filepath.append(mainFilePath);
    }
    if (prefix != null) {
      filepath.append(prefix);
    }
    filesToWrite[0] = filepath + "integers.txt";
    filesToWrite[1] = filepath + "floats.txt";
    filesToWrite[2] = filepath + "strings.txt";
    if (!optionA) {
      for (String filePath : filesToWrite) {
        File file = new File(filePath);
        file.delete();
      }
    }
  }

  /**
   * This method puts elements into concrete file with the corresponding data type
   *
   * @param element  element that we want to put
   * @param filePath path to the file with the corresponding data type
   */
  public static void writeIntoFile(String element, String filePath) {
    File file = new File(String.valueOf(filePath));
    if (!file.exists()) {
      try {
        if (!file.createNewFile()) {
          System.out.println("Error in creating file!");
        }
      } catch (IOException e) {
        System.err.println("Создание файла");
        e.printStackTrace();
      }
    }

    //В тестовом задании, которое вы дали, была непонятна одна вещь. Допустим у нас в файле
    //уже записана строка "hello". Пользователь вводит "-a" при запуске, что означает, по
    //вашему условию, что новые данные добавляются в старые. Но если в новых данных мы опять
    //встречаем слово "hello", мы его игнорируем или тоже записываем в файл со строками?

    try (
        BufferedWriter bufferedWriter = new BufferedWriter(
            new FileWriter(filePath, true))) {
      bufferedWriter.write(element + "\n");
    } catch (IOException e) {
      System.err.println("Изменение файла");
      e.printStackTrace();
    }
  }

  /**
   * This method sorts all the elements that we got from user's file
   * between three files that contain different data types
   *
   * @param filename name of the file which elements we are going to sort
   * @throws IOException exception that may occur while reading from file
   */
  public static void sortFileElements(String filename) throws IOException {
    File file = new File(filename);
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
      while (bufferedReader.ready()) {
        String line = bufferedReader.readLine();
        int lineType;

        try {
          Integer.parseInt(line);
          lineType = 0;
          writeIntoFile(line, filesToWrite[lineType]);
        } catch (NumberFormatException e) {
          lineType = 2;
        }

        if (lineType == 2) {
          try {
            Float.parseFloat(line);
            lineType = 1;
            writeIntoFile(line, filesToWrite[lineType]);
          } catch (NumberFormatException e) {
            lineType = 2;
          }
        }

        if (lineType == 2) {
          writeIntoFile(line, filesToWrite[lineType]);
        }
      }
    }
  }

  /**
   * This method is used to read and to write all the arguments that we had got from user.
   *
   * @param args arguments that we got
   * @return true if the process was successful, otherwise - false
   */
  public static boolean processArguments(String[] args) {
    for (int i = 0; i < args.length; i++) {
      if (args[i].compareTo("-o") == 0 && i + 1 < args.length) {
        File file = new File(args[i + 1]);
        if (!file.mkdirs() && !file.isDirectory()) {
          System.err.println("Error in creating directory!");
          return false;
        }
        ++i;
        if (args[i].charAt(args[i].length() - 1) != File.separatorChar) {
          mainFilePath = args[i] + File.separator;
        } else {
          mainFilePath = args[i];
        }
      } else if (args[i].compareTo("-p") == 0 && i + 1 < args.length) {
        prefix = args[++i];
      } else if (args[i].compareTo("-a") == 0) {
        optionA = true;
      } else if ((args[i].compareTo("-s") == 0 && statisticsType == StatisticsType.FULL) ||
          (args[i].compareTo("-f") == 0 && statisticsType == StatisticsType.SHORT)) {
        System.err.println("You have mentioned several statistic's types! First one will be used.");
      } else if (args[i].compareTo("-s") == 0) {
        statisticsType = StatisticsType.SHORT;
        System.out.println("Short");
      } else if (args[i].compareTo("-f") == 0) {
        statisticsType = StatisticsType.FULL;
        System.out.println("Full");
      } else {
        File file = new File(args[i]);
        if (file.isFile()) {
          filesToCheck.add(args[i]);
        }
      }
    }
    return true;
  }

}