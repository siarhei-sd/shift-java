package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FloatData {

    int countFloat = 0;

    float sumFloat = 0;
    float averageFloat = 0.0f;
    float maxFloat = 0, minFloat = 0;

    public void WriteIntoFile(ArrayList<Float> arrayList, String outFile, boolean rewrite) {
        if (!arrayList.isEmpty()) {
            try (FileWriter fileWriter = new FileWriter(outFile, rewrite)) {
                System.out.print("\nЗапись в файл: " + outFile);

                maxFloat = arrayList.get(0);
                minFloat = arrayList.get(0);
                countFloat = arrayList.size();
                for (int i = 0; i < arrayList.size(); i++) {
                    sumFloat += arrayList.get(i);
                    maxFloat = Math.max(maxFloat, arrayList.get(i));
                    minFloat = Math.min(minFloat, arrayList.get(i));
                    fileWriter.write(arrayList.get(i).toString() + "\n");
                }
                averageFloat = sumFloat / 2;
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void PrintStatistic(boolean fullStat) {
        // вывод краткой статистики
        if (!fullStat) {
            System.out.println("\n\nКоличество элементов типа FLOAT: " + countFloat);
        }
        // вывод полной статистики
        else {
            System.out.println("\n\nКоличество элементов типа FLOAT: " + countFloat +
                    "\nСумма элементов типа FLOAT: " + sumFloat + "\nСреднее значение элементов типа FLOAT: "
                    + averageFloat + "\nМаксимальное значение среди элементов типа FLOAT: " + maxFloat
                    + "\nМинимальное значение среди элементов типа FLOAT: " + minFloat);
        }
    }
}
