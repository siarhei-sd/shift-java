package com.utility.control;

import com.utility.files_operating.FilesOperator;

import java.nio.file.Path;

import static com.utility.management.ArgumentsReader.*;

public class Utility {

    public static void main(String[] args) {
        read(args);
        Path[] files= getSourceFiles();
        if (files.length>0){
            FilesOperator.settingsSetter(getPATH(), getPREFIX(), isAPPEND(), isFULL(), isSHORT());
            String Statistics=FilesOperator.operate(files);
            System.out.println("\n"+Statistics);
            System.out.print("End");
        } else System.out.println("No files to operate");
    }
}
