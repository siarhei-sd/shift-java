import java.io.*;
import java.util.*;

public class FileFilter {
    String pathInFile = "D:\\Study\\FileFilter\\Files\\";
    String pathOutFiles = "D:\\Study\\FileFilter\\Files\\";
    String nameFile = "";
    boolean flagOnWrite = false;
    boolean flagShortStatistic = false;
    boolean flagFullStatistics = false;
    List<String> fileNames = new ArrayList<>();
    List<Integer> integerList = new ArrayList<>();
    List<Float> floatList = new ArrayList<>();
    List<String> stringList = new ArrayList<>();
    List<String> strings = new ArrayList<>();

    public void readArguments(String[] args) throws IOException {

        for (int i = 0; i < args.length; i++) {
            if (args[i].matches("-a")) {
                flagOnWrite = true;
            }
            if (args[i].matches("-o")) {
                pathOutFiles = args[i+1];
            }
            if (args[i].matches("-p")) {
                pathOutFiles += args[i+1];
                nameFile += args[i+1];
            }
            if (args[i].matches("-s")) {
                flagShortStatistic = true;
            }
            if (args[i].matches("-f")) {
                flagFullStatistics = true;
            }
            if (args[i].matches("^[a-zA-Z0-9_]*\\.txt$")){
                fileNames.add(args[i]);
            }
        }

        if (!fileNames.isEmpty()) {
            for (String name : fileNames) {
                readFile(pathInFile+name);
            }
            findData(strings);
        }
        else {
            System.out.println("No input files! Please enter input file(s) and parameters again...");

            Scanner in = new Scanner(System.in);
            String inputString = in.nextLine();
            String[] inputData = inputString.split(" ");
            readArguments(inputData);
        }
    }

    public void readFile(String path) {

        File file = new File(path);
        Scanner scanner;
        try {
            scanner = new Scanner(file, "UTF-8");

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                strings.add(line);
            }
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void findData(List<String> strings) throws IOException {

        for (String string : strings) {
            if ((string.matches("[[A-Za-zА-яЁё]+\\s*]+"))) {
                stringList.add(string);
            }
            else if (string.matches("^-?[0-9]*[.,][0-9]+[E]?[-]?[0-9]*$")) {
                floatList.add(Float.valueOf(string));
            }
            else if (string.matches("-?\\d+")) {
                integerList.add(Integer.valueOf(string));
            }
            else if (string.matches("[A-Za-zА-пр-ё\\s*-?\\d+]+")) {
                String[] worlds = string.split(" ");
                String collectionWords = " ";
                for (int i = 0; i < worlds.length; i++) {
                    if (worlds[i].matches("[A-Za-zА-пр-ё]+")) {
                        collectionWords += " " + worlds[i];
                    }
                    else {
                        integerList.add(Integer.valueOf(worlds[i]));
                    }
                }
                stringList.add(collectionWords.trim());
            }
        }
        writeInFile(integerList, floatList, stringList, pathOutFiles, flagOnWrite);

        statistic(integerList, floatList, stringList, flagShortStatistic, flagFullStatistics, nameFile);
    }

    public void writeInFile(List<Integer> integerList, List<Float> floatList,
                            List<String> stringList, String pathOutFiles, boolean flagOnWrite) throws IOException {

        if (!integerList.isEmpty()) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(pathOutFiles + "integers.txt", flagOnWrite));
            for (Integer integer : integerList) {
                writer.write(integer + "\n");
            }
            writer.close();
        }

        if (!floatList.isEmpty()) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(pathOutFiles + "floats.txt", flagOnWrite));
            for (Float flList : floatList) {
                writer.write(flList + "\n");
            }
            writer.close();
        }

        if (!stringList.isEmpty()) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(pathOutFiles + "strings.txt", flagOnWrite));
            for (String string : stringList) {
                writer.write(string + "\n");
            }
            writer.close();
        }
    }

    public void statistic(List<Integer> integerList, List<Float> floatList, List<String> stringList,
                          boolean flagShortStatistic, boolean flagFullStatistics, String nameFile) {
        if (flagShortStatistic) {
            if (!integerList.isEmpty()) {
                System.out.println(nameFile+"integers.txt short statistic: elements: " + integerList.size());
            }
            if (!floatList.isEmpty()) {
                System.out.println(nameFile+"floats.txt short statistic: elements: " + floatList.size());
            }
            if (!stringList.isEmpty()) {
                System.out.println(nameFile+"strings.txt short statistic: elements: " + stringList.size());
            }
        }

        if (flagFullStatistics) {
            if (!integerList.isEmpty()) {
                int min = Collections.min(integerList);
                int max = Collections.max(integerList);

                int sum = 0;
                for (Integer item : integerList) {
                    sum += item;
                }

                int average = sum / integerList.size();

                System.out.println(nameFile + "integers.txt full statistic: elements: " + integerList.size() +
                        "; min = " + min + "; max = " + max + "; sum = " + sum + "; average = " + average);
            }

            if (!floatList.isEmpty()) {
                float min = Collections.min(floatList);
                float max = Collections.max(floatList);

                float sum = 0;
                for (Float item : floatList) {
                    sum += item;
                }

                float average = sum / floatList.size();

                System.out.println(nameFile + "floats.txt full statistic: elements: " + floatList.size() +
                        "; min = " + min + "; max = " + max + "; sum = " + sum + "; average = " + average);
            }

            if (!stringList.isEmpty()) {

                int min = stringList.get(0).length();
                int max = min;
                for (String item : stringList) {
                    if (item.length() < min) {
                        min = item.length();
                    }
                    if (item.length() > max) {
                        max = item.length();
                    }
                }

                System.out.println(nameFile + "strings.txt full statistic: elements: " + stringList.size() +
                        "; min length record = " + min + "; max = length record " + max);
            }
        }
    }
}