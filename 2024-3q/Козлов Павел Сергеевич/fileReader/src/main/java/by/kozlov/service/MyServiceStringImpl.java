package by.kozlov.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MyServiceStringImpl implements MyService{

    private final List<String> resultString = new ArrayList<>();
    private final String nameOfSave;

    public MyServiceStringImpl(String nameOfSave) {
        this.nameOfSave = nameOfSave;
    }
    @Override
    public List<String> finder(List<String> args) {
        for(String line: args) {
            String result3 = Pattern.compile("[a-zA-ZА-Яа-я]+[^0-9\\s\\W]*")
                    .matcher(line)
                    .results()
                    .map(MatchResult::group)
                    .collect(Collectors.joining(" "));
            if (!result3.isEmpty()) resultString.add(result3);
        }
        return resultString;
    }

    @Override
    public void minStat() {
        if (!resultString.isEmpty()) System.out.println(nameOfSave +
                " short statistic: elements = " + resultString.size());
    }

    @Override
    public void maxStat() {
        if (!resultString.isEmpty()) {
            Integer minStr = resultString.stream().map(String::length).min(Integer::compare).orElse(0);
            Integer maxStr = resultString.stream().map(String::length).max(Integer::compare).orElse(0);
            System.out.println(nameOfSave +
                    " full statistic: elements = " + resultString.size() + "; min string = "
                    +  minStr + "; max string = " + maxStr + ";"
            );
        }
    }

    @Override
    public String getNameOfSave() {
        return nameOfSave;
    }
}
