package com.utility.files_operating;

import com.utility.control.ContentFilter;
import com.utility.management.FullStatisticGather;
import com.utility.management.StatisticGather;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FilesOperator {

    private FilesOperator() {}

    public static String operate(Path[] files){
        for (Path file : files) {
            try(BufferedReader reader = Files.newBufferedReader(file)){
                String line;
                while ((line=reader.readLine())!=null){
                    line=line.trim();
                    ContentFilter.filter(line);
                }
            }catch (IOException e){
                System.out.println("Error while opening or reading file : \"" + file+"\"");
                return "Utility failed to read files";
            } catch (RuntimeException e){
                return "Utility failed to write files";
            }
        }
        return FilesWriter.closeWriter();
    }

    public static void settingsSetter(Path path, String prefix, boolean append, boolean full, boolean aShort) {
        StatisticGather gather = null;
        if(full) gather =new FullStatisticGather();
        else if (aShort) gather = new StatisticGather();
        String directory = path.toString();
        FilesWriter.settingsSetter(directory, prefix, append, gather);
    }
}
