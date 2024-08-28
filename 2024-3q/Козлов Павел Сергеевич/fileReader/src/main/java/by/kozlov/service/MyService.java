package by.kozlov.service;

import java.util.List;

public interface MyService {
    List<String> finder(List<String> args);
    void minStat();
    void maxStat();
    String getNameOfSave();
}
