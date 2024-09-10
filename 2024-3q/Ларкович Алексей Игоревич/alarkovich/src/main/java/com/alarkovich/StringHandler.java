package com.alarkovich;

public class StringHandler extends DataHandler {

    private final static String FORMAT_PATTERN = "%s full statistic: elements = %d; minLength = %d; maxLength = %d;";

    private final static String FILE_NAME = "strings.txt";

    public StringHandler(String path, String prefix, Boolean append) {
        super();

        this.fileName = FILE_NAME;

        this.initWriter(path, prefix, append);
    }

    public StringHandler writeFullStats() {
        Integer min = this.dataList.get(0).length();
        Integer max = this.dataList.get(0).length();

        for (int i = 1; i < this.dataList.size(); i++) {
            if (min > this.dataList.get(i).length()) min = this.dataList.get(i).length();
            if (max < this.dataList.get(i).length()) max = this.dataList.get(i).length();
        }

        System.err.println(String.format(
            FORMAT_PATTERN,
            this.fileNameWithPrefix,
            this.dataList.size(),
            min,
            max
        ));

        return this;
    }
}
