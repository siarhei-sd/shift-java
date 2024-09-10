package com.alarkovich;

public class FloatHandler extends DataHandler {

    private final static String FORMAT_PATTERN = "%s full statistic: elements = %d; min = %f; max = %f; sum = %f, average = %f;";

    private final static String FILE_NAME = "floats.txt";

    public FloatHandler(String path, String prefix, Boolean append) {
        super();

        this.fileName = FILE_NAME;

        this.initWriter(path, prefix, append);
    }

    public FloatHandler writeFullStats() {
        Float min = Float.valueOf(this.dataList.get(0));
        Float max = Float.valueOf(this.dataList.get(0));
        Float sum = Float.valueOf(this.dataList.get(0));

        for (int i = 1; i < this.dataList.size(); i++) {
            Float item = Float.valueOf(this.dataList.get(i));

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
            Float.valueOf(sum / Float.valueOf(this.dataList.size()))
        ));
        return this;
    }
}
