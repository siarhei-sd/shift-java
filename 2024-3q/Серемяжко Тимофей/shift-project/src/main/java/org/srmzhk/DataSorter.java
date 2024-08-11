package org.srmzhk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataSorter {
    private List<String> integersList;
    private List<String> floatsList;
    private List<String> stringsList;
    private static final RegexHelper regexHelper = new RegexHelper();
    private static Statistic statistic;

    public DataSorter(Statistic statistic) {
        this.integersList = new ArrayList<>();
        this.floatsList = new ArrayList<>();
        this.stringsList = new ArrayList<>();
        DataSorter.statistic = statistic;
    }

    public void sort(String[] inputFiles) {
        for (String inputFilePath : inputFiles) {
            try {
                File file = new File(inputFilePath);
                if(!file.exists())
                    throw new IOException("Can't read input file. Wrong input file path");

                String buf = "";
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                while ((buf = bufferedReader.readLine()) != null) {
                    if (regexHelper.isInteger(buf)) {
                        integersList.add(buf);
                        statistic.countInteger(buf);
                    }
                    else if (regexHelper.isFloat(buf)) {
                        floatsList.add(buf);
                        statistic.countFloat(buf);
                    }
                    else {
                        stringsList.add(buf);
                        statistic.countString(buf);
                    }
                }
                bufferedReader.close();

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public List<String> getIntegers() {
        return integersList;
    }

    public List<String> getFloats() {
        return floatsList;
    }

    public List<String> getStrings() {
        return stringsList;
    }
}
