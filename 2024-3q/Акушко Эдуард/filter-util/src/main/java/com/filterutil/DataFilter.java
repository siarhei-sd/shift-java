package com.filterutil;

import lombok.Getter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class DataFilter {
    private List<Integer> integers = new ArrayList<>();
    private List<Double> floats = new ArrayList<>();
    private List<String> strings = new ArrayList<>();

    public void filterData(String line) {
        // Регулярное выражение для поиска целых и вещественных чисел
        Pattern numberPattern = Pattern.compile("-?\\d+(\\.\\d+)?([eE][-+]?\\d+)?");
        Matcher numberMatcher = numberPattern.matcher(line);

        // Регулярное выражение для поиска цифр в строке
        Pattern digitPattern = Pattern.compile("\\d");
        Matcher digitMatcher = digitPattern.matcher(line);

        boolean hasNumbers = false;

        while (numberMatcher.find()) {
            String number = numberMatcher.group();
            try {
                // Попытка распарсить строку как целое число
                integers.add(Integer.parseInt(number));
                hasNumbers = true;
            } catch (NumberFormatException e1) {
                try {
                    // Попытка распарсить строку как вещественное число
                    floats.add(Double.parseDouble(number));
                    hasNumbers = true;
                } catch (NumberFormatException e2) {
                    // Если не удалось распарсить как число, добавляем строку
                    strings.add(number);
                }
            }
        }

        // Если строка не содержит чисел и не содержит цифр, добавляем её в список строк
        if (!hasNumbers && !digitMatcher.find()) {
            strings.add(line);
        }
    }
}