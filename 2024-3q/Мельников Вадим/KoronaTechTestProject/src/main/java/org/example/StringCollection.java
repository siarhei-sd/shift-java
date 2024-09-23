package org.example;
import java.io.FileWriter;
import java.io.IOException;

public class StringCollection extends BaseCollection implements ICollectStatistics{
    private int longest, shortest;

    public StringCollection() {
        super();
    }

    @Override
    public boolean ManageString(String str) {
        sources.add(str);
        return true;
    }

    //Запись строк в файл, возвращает true в случае успеха, false в случае ошибки
    @Override
    public boolean writeToFile(String path, boolean appendFlag) {

        //Требование не создавать пустой файл!
        if (sources.isEmpty()) {
            return true;
        }
        //
        try (FileWriter fWriter = new FileWriter(path, appendFlag)) {
            longest = sources.get(0).length();
            shortest = sources.get(0).length();
            super.writeToFile(path, appendFlag);

            int strlen;
            for (int i = 0; i < sources.size(); i++) {
                strlen = sources.get(i).length();
                fWriter.write(sources.get(i) + '\n');
                shortest = (strlen < shortest) ? strlen : shortest;
                longest = (strlen > longest) ? strlen : longest;
                count++;
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    @Override
    public String getFullStatistics() {
        String result;
        if (!wasSaved) {
            result = "Запись в файл не производилась";
        } else {
            result = getShortStatistics() +
                    "; Longest string " + longest +
                    "; Shortest string " + shortest;
        }
        return result;
    }
}
