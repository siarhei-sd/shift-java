package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class StringData {
    int countString = 0;

    int maxStringLength = 0, minStringLength = 0;

    public void WriteIntoFile(ArrayList<String> arrayList, String outFile, boolean rewrite) {
        if (!arrayList.isEmpty()) {
            try (FileWriter fileWriter = new FileWriter(outFile, rewrite)) {
                System.out.print("\nЗапись в файл: " + outFile);

                maxStringLength = arrayList.get(0).length();
                minStringLength = arrayList.get(0).length();
                countString = arrayList.size();
                for (int i = 0; i < arrayList.size(); i++) {
                    maxStringLength = Math.max(maxStringLength, arrayList.get(i).length());
                    minStringLength = Math.min(minStringLength, arrayList.get(i).length());
                    fileWriter.write(arrayList.get(i) + "\n");
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void PrintStatistic(boolean fullStat) {
        // вывод краткой статистики
        if (!fullStat)
        {
            System.out.println("\n\nКоличество элементов типа STRING: " + countString);
        }
        // вывод полной статистики
        else
        {
            System.out.println("\n\nКоличество элементов типа STRING: " + countString +
                    "\nРазмер самой длинной строки: " + maxStringLength
                    + "\nРазмер самой короткой строки: " + minStringLength);
        }
    }
}
