package com.utility.control;

import com.utility.files_operating.FilesWriter;

public class ContentFilter {

    private ContentFilter() {}

    public static void filter(String line){
        if (Validator.isLong(line)) FilesWriter.writeLong(line);
        else if (Validator.isDouble(line)) FilesWriter.writeDouble(line);
        else if (!line.isEmpty()) FilesWriter.writeString(line);
    }
}
