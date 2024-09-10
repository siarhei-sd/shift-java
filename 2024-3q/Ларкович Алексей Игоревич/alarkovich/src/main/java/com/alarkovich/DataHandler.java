package com.alarkovich;
import java.util.ArrayList;

public class DataHandler {

    protected ArrayList<String> dataList = new ArrayList<String>();

    protected FileHandler fileWriter;

    protected String fileName = "results.txt";
    protected String fileNameWithPrefix = "";

    protected DataHandler() {}

    protected DataHandler initWriter(String path, String prefix, Boolean append) {
        this.fileNameWithPrefix = (prefix != null ? prefix : "") + fileName;
        this.fileWriter         = new FileHandler(path, this.fileNameWithPrefix, append);

        return this;
    }

    public DataHandler setData(ArrayList<String> data) {
        this.dataList = data;
        return this;
    }

    public DataHandler writeToFile() {
        this.fileWriter.writeToFile(this.dataList);
        return this;
    }

    public DataHandler writeStats(Boolean fullStats, Boolean shortStats) {
        if (this.dataList.size() > 0) {
            if (fullStats) {
                writeFullStats();
            } else if (shortStats) {
                writeShortStats();
            }
        }

        return this;
    }

    protected DataHandler writeShortStats() {
        System.out.println(String.format(
            "%s short statistic: elements = %d;",
            this.fileNameWithPrefix,
            this.dataList.size()
        ));
        return this;
    }

    protected DataHandler writeFullStats() {
        return this;
    }
}
