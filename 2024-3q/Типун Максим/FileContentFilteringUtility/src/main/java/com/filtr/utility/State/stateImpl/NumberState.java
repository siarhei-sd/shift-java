package com.filtr.utility.State.stateImpl;

import com.filtr.utility.State.State;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * Файлы целочисленные и с плавающей точкой
 */
public class NumberState implements State {
    public NumberState(boolean haveStatistic, boolean fullStatistic){
        this.haveStatistic = haveStatistic;
        this.fullStatistic = fullStatistic;
    }

    private final boolean fullStatistic;
    private final boolean haveStatistic;

    //Поля для перезаписи при сравнении
    private double minimum = 50000000;
    private double maximum = -5000000;
    private double amount = 0;
    private long count = 0;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getLineCount() {
        return count;
    }

    public void setLineLength(long count) {
        this.count = count;
    }

    public double getMinimum() {
        return minimum;
    }

    public double getMaximum() {
        return maximum;
    }

    public void setMinimum(double minimum) {
        this.minimum = minimum;
    }

    public void setMaximum(double maximum) {
        this.maximum = maximum;
    }


    public void analysisAndPrint(File file){
        if (file.exists()){
            Scanner scanner;
            try {
                scanner = new Scanner(file);
            } catch (FileNotFoundException e) {
                //ошибка не выкинется, она обработается выше
                throw new RuntimeException(e);
            }

            while (scanner.hasNextLine()){
                float lineValue = Float.parseFloat(scanner.nextLine());
                setAmount(getAmount()+ lineValue);
                setLineLength(getLineCount() + 1);
                if (lineValue > getMaximum()){
                    setMaximum(lineValue);
                }
                if (lineValue < getMinimum()){
                    setMinimum(lineValue);
                }
            }
            if (haveStatistic){
                System.out.println("statistics: " + file.getName());
                System.out.println(" elements: " + getLineCount());
                if (fullStatistic){
                    printFullNumericStatistic();
                }
            }

            setLineLength(0);
        } else {
            System.out.println("File not exist: " + file.getName());
        }
    }

    //Полная статистика.
    public void printFullNumericStatistic() {

        DecimalFormat decimalFormat = new DecimalFormat("#.##########");

        System.out.println("\t Full statistic:\t");
        System.out.println("\t\t min: " + decimalFormat.format(getMinimum()));
        System.out.println("\t\t max: " + decimalFormat.format(getMaximum()));
        System.out.println("\t\t sum: " + decimalFormat.format(amount));
        System.out.println("\t\t average: " + decimalFormat.format(amount / getLineCount()));
        System.out.println();
    }
}
