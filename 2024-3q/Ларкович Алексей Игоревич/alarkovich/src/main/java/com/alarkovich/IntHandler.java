package com.alarkovich;

public class IntHandler extends DataHandler {

    private final static String FORMAT_PATTERN = "%s full statistic: elements = %d; min = %d; max = %d; sum = %d, average = %d;";

    private final static String FILE_NAME = "ints.txt";

    public IntHandler(String path, String prefix, Boolean append) {
        super();

        this.fileName = FILE_NAME;

        this.initWriter(path, prefix, append);
    }

    public IntHandler writeFullStats() {
        Integer min = Integer.valueOf(this.dataList.get(0));
        Integer max = Integer.valueOf(this.dataList.get(0));
        Integer sum = Integer.valueOf(this.dataList.get(0));

        for (int i = 1; i < this.dataList.size(); i++) {
            Integer item = Integer.valueOf(this.dataList.get(i));

            if (min > item) min = item;
            if (max < item) max = item;
            
            sum += item;
        }

        System.err.println(String.format(
            FORMAT_PATTERN,
            this.fileNameWithPrefix,
            this.dataList.size(),
            min,
            max,
            sum,
            Integer.valueOf(sum / this.dataList.size())
        ));

        return this;
    }
}
