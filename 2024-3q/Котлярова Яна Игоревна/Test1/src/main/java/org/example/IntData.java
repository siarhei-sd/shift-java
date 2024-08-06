package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class IntData {

    int countInt = 0;

    int sumInt = 0;
    float averageInt = 0.0f;
    int maxInt = 0, minInt = 0;

    public void WriteIntoFile(ArrayList<Integer> arrayList, String outFile, boolean rewrite)
    {
        if (!arrayList.isEmpty()) {
            try (FileWriter fileWriter = new FileWriter(outFile, rewrite)) {
                System.out.print("\nЗапись в файл: " + outFile);

                maxInt = arrayList.get(0);
                minInt = arrayList.get(0);
                countInt = arrayList.size();
                for (int i = 0; i < arrayList.size(); i++)
                {
                    sumInt += arrayList.get(i);
                    maxInt = Math.max(maxInt, arrayList.get(i));
                    minInt = Math.min(minInt, arrayList.get(i));
                    fileWriter.write(arrayList.get(i).toString() + "\n");
                }
                averageInt = (float) sumInt/2;
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void PrintStatistic(boolean fullStat)
    {
        // вывод краткой статистики
        if (!fullStat)
        {
            System.out.println("\n\nКраткая статистика:\nКоличество элементов типа INT: " + countInt);
        }
        // вывод полной статистики
        else
        {
            System.out.println("\n\nПолная статистика:\nКоличество элементов типа INT: " + countInt +
                    "\nСумма элементов типа INT: " + sumInt + "\nСреднее значение элементов типа INT: "
                    + averageInt + "\nМаксимальное значение среди элементов типа INT: " + maxInt
                    + "\nМинимальное значение среди элементов типа INT: " + minInt);
        }
    }
}
