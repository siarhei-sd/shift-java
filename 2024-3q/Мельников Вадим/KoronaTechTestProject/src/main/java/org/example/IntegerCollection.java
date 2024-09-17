package org.example;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class IntegerCollection extends BaseCollection implements ICollectStatistics {

    private ArrayList<Integer> numbers;
    private int min, max, sum;

    public IntegerCollection() {
        super();
        numbers = new ArrayList<Integer>();
    }

    public boolean ManageString(String str) {
        try {
            int num = Integer.parseInt(str);
            numbers.add(num);
            sources.add(str);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    //Запись строк в файл, возвращает true в случае успеха, false в случае ошибки
    @Override
    public boolean writeToFile(String path, boolean appendFlag) {

        //Требование не создавать пустой файл!
        if (numbers.isEmpty()) {
            return true;
        }
        //
        try (FileWriter fWriter = new FileWriter(path, appendFlag)) {
            min = numbers.get(0);
            max = numbers.get(0);
            sum = 0;
            super.writeToFile(path, appendFlag);

            int ni;
            for (int i = 0; i < sources.size(); i++) {
                ni = numbers.get(i);
                fWriter.write(sources.get(i) + '\n');
                min = (ni < min) ? ni : min;
                max = (ni > max) ? ni : max;
                sum += ni;
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
                    "; Min " + min +
                    "; Max " + max +
                    "; Sum " + sum +
                    "; Avg " + ((float) sum / count);
        }
        return result;
    }
}
