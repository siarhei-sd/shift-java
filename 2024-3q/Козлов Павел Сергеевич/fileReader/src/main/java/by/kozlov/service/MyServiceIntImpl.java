package by.kozlov.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MyServiceIntImpl implements MyService {

    private final List<Integer> resultInteger = new ArrayList<>();
    private final String nameOfSave;

    public MyServiceIntImpl(String nameOfSave) {
        this.nameOfSave = nameOfSave;
    }

    public String getNameOfSave() {
        return nameOfSave;
    }

    @Override
    public List<String> finder(List<String> args) {
        for (String line : args) {
            List<Integer> result = Pattern.compile("(?<![.\\d])[-+]?\\d+(?![.\\d])")
                    .matcher(line)
                    .results()
                    .map(MatchResult::group)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            resultInteger.addAll(result);
        }
        return resultInteger.stream().map(String::valueOf).collect(Collectors.toList());
    }

    @Override
    public void minStat() {
        if (!resultInteger.isEmpty()) System.out.println(nameOfSave +
                " short statistic: elements = " + resultInteger.size());
    }

    @Override
    public void maxStat() {
        if (!resultInteger.isEmpty()) {
            System.out.println(nameOfSave +
                    " full statistic: elements = " + resultInteger.size() + "; min = "
                    + resultInteger.stream().min(Integer::compare).orElse(0) + "; max = "
                    + resultInteger.stream().max(Integer::compare).orElse(0) + "; sum = "
                    + resultInteger.stream().reduce(0, Integer::sum) + "; average = "
                    + resultInteger.stream().mapToInt(Integer::intValue).average().orElse(0) + ";"
            );
        }
    }
}
