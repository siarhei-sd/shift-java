package com.korona.script.util;

import java.util.*;

public class Statistics {

    public static void getStatistic(List<String> list, String fileName, boolean isShortStatistic, boolean isFullStatistic, String type) {
        int sizeList = list.size();
        if (isShortStatistic) {
            System.out.println(fileName + " short statistic: elements = " + sizeList);
        } else if (isFullStatistic) {
            if (type.equals("floats")) {
                double sum = list.stream()
                        .mapToDouble(a -> Double.parseDouble(a))
                        .sum();
                var min = list.stream()
                        .mapToDouble(a -> Double.parseDouble(a))
                        .min();
                var max = list.stream()
                        .mapToDouble(a -> Double.parseDouble(a))
                        .max();
                var average = list.stream()
                        .mapToDouble(a -> Double.parseDouble(a))
                        .average();
                System.out.println(fileName + " full statistic: elements = " + sizeList + "; min= " + min + "; max= " + max +
                        "; sum= " + sum + "; average= " + average);
            } else if (type.equals("integers")) {
                double sum = list.stream()
                        .mapToInt(a -> Integer.parseInt(a))
                        .sum();
                var min = list.stream()
                        .mapToInt(a -> Integer.parseInt(a))
                        .min();
                var max = list.stream()
                        .mapToInt(a -> Integer.parseInt(a))
                        .max();
                var average = list.stream()
                        .mapToInt(a -> Integer.parseInt(a))
                        .average();
                System.out.println(fileName + " full statistic: elements = " + sizeList + "; min= " + min + "; max= " + max +
                        "; sum= " + sum + "; average= " + average);
            }else if (type.equals("strings")) {
                try {
                    int max = Collections.max(list, Comparator.comparing(s -> s.length())).length();
                    int min = Collections.min(list, Comparator.comparing(s -> s.length())).length();
                    System.out.println(fileName + " full statistic: elements = " + sizeList + "; min= " + min + "; max= " + max);
                }catch (NoSuchElementException e){
                    System.out.println("По  строкам нет данных, т.к. нет строк");
                }


            }

        }


    }

}
