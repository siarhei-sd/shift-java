package com.TestTask;

import com.TestTask.filemanager.FileManager;

public class Main {
    public static void main(String[] args) {

        FileManager fileManager = new FileManager(args);
        fileManager.create();
    }
}
