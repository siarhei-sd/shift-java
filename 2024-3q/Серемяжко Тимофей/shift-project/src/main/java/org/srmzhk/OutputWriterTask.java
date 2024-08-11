package org.srmzhk;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

public class OutputWriterTask implements Callable<Void> {
    private final String outputFilePath;
    private final List<String> dataToWrite;
    private static boolean isAppend = false;

    public OutputWriterTask(String outputFilePath, List<String> dataToWrite) {
        this.outputFilePath = outputFilePath;
        this.dataToWrite = dataToWrite;
    }

    public static void setAppendState(boolean state) {
        isAppend = state;
    }

    public Void call() throws Exception{
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath, isAppend));
            for (String str : dataToWrite) {
                writer.write(str);
                writer.newLine();
            }
            writer.close();
        }
        catch (FileNotFoundException e) {
            throw new InterruptedException("Wrong path for output files. The path can't be reached");
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}