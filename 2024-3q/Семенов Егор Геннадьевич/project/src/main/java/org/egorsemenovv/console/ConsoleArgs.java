package org.egorsemenovv.console;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ConsoleArgs {
    private final List<String > fileNames;
    private final Map<String, Boolean> options;
    private String path;
    private String prefix;

    public ConsoleArgs(){
        fileNames = new ArrayList<>();
        options = new HashMap<>();
        path = "";
        prefix = "";
    }

    public void addFileName(String fileName) {
        if (!fileNames.contains(fileName)){
            fileNames.add(fileName);
        }
    }

    public void addOption(String key, boolean value){
        if(!options.containsKey(key)){
            options.put(key, value);
        }
    }

    public boolean hasOption(String option){
        return options.getOrDefault(option, false);
    }

}
