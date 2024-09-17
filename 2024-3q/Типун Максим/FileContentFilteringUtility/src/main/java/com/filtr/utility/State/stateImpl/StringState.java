package com.filtr.utility.State.stateImpl;

import com.filtr.utility.State.State;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * строковые файлы.
 */
public class StringState implements State {
    public StringState(boolean haveStatistic, boolean fullStatistic){
        this.haveStatistic = haveStatistic;
        this.fullStatistic = fullStatistic;
    }

    private final boolean haveStatistic;
    private final boolean fullStatistic;

    private double minimumLength;
    private double maximumLength;
    private long lineCount = 0;

    public long getCount() {
        return lineCount;
    }

    public long getLineCount() {
        return lineCount;
    }

    public void setLineLength(long count) {
        this.lineCount = count;
    }

    public double getMinimumLength() {
        return minimumLength;
    }

    public double getMaximumLength() {
        return maximumLength;
    }

    public void setMinimumLength(double minimumLength) {
        this.minimumLength = minimumLength;
    }

    public void setMaximumLength(double maximumLength) {
        this.maximumLength = maximumLength;
    }


    public void analysisAndPrint(File file){
        if (file.exists()){
            // Первоначальные настройки для корректной итерации, что бы было с чем сравнить.
            setMinimumLength(50000000);
            setMaximumLength(-50000000);
            Scanner scanner;

            try {
                scanner = new Scanner(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            while (scanner.hasNextLine()){
                // подсчет строк
                setLineLength(getCount() + 1);
                String lineValue = String.valueOf(scanner.nextLine());
                if (lineValue.length() > getMaximumLength()){
                    setMaximumLength(lineValue.length());
                }
                if (lineValue.length() < getMinimumLength()){
                    setMinimumLength(lineValue.length());
                }
            }
            if(haveStatistic){
                System.out.println("statistics: " + file.getName());
                System.out.println("elements: " + getLineCount());
                if (fullStatistic){
                    printFullStringResult();
                }
            }
            //что бы в цикле корректно выводить количество элементов
            setLineLength(0);

        } else {
            System.out.println("File not exist: " + file.getName());
        }
    }

    public void printFullStringResult(){
        System.out.println("\t details:\t");
        System.out.println("\t\t minimum length: " + getMinimumLength());
        System.out.println("\t\t maximum length: " + getMaximumLength());
        System.out.println();
    }
}
