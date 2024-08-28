package by.kozlov.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MyServiceDoubleImpl implements MyService{

    private final List<Double> resultDouble = new ArrayList<>();
    private final String nameOfSave;

    public MyServiceDoubleImpl(String nameOfSave) {
        this.nameOfSave = nameOfSave;
    }
    @Override
    public List<String> finder(List<String> args) {
        for(String line: args) {
            List<Double> result1 = Pattern.compile("[-+]?[0-9]*[.,][0-9]+(?:[eE][-+]?[0-9]+)?")
                    .matcher(line)
                    .results()
                    .map(MatchResult::group)
                    .map(Double::parseDouble)
                    .collect(Collectors.toList());
            resultDouble.addAll(result1);
        }
        return resultDouble.stream().map(String::valueOf).collect(Collectors.toList());

    }

    @Override
    public void minStat() {
        if (!resultDouble.isEmpty()) System.out.println(nameOfSave +
                " short statistic: elements = " + resultDouble.size());
    }

    @Override
    public void maxStat() {
        if (!resultDouble.isEmpty()) {
            System.out.println(nameOfSave +
                    " full statistic: elements = " + resultDouble.size() + "; min = "
                    + resultDouble.stream().min(Double::compare).orElse(0.0) + "; max = "
                    + resultDouble.stream().max(Double::compare).orElse(0.0) + "; sum = "
                    + resultDouble.stream().reduce(0.0,Double::sum) + "; average = "
                    + resultDouble.stream().mapToDouble(Double::doubleValue).average().orElse(0.0) + ";"
            );
        }
    }

    @Override
    public String getNameOfSave() {
        return nameOfSave;
    }
}
