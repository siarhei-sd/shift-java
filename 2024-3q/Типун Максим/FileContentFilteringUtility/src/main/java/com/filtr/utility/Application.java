package com.filtr.utility;

import com.filtr.utility.State.State;
import com.filtr.utility.State.stateImpl.NumberState;
import com.filtr.utility.FileExecutive.FileExecutive;
import com.filtr.utility.ArgsExecutive.ArgsExecutive;
import com.filtr.utility.State.stateImpl.StringState;

public class Application {
    public static void main(String[] args) {
        ArgsExecutive argsExecutive = new ArgsExecutive();
        argsExecutive.parseProgramArguments(args);


        FileExecutive fileExecutive = new FileExecutive(argsExecutive.isAppendMode(), argsExecutive.getOutputPath(), argsExecutive.getFilePrefix());


        State integerState = new NumberState(argsExecutive.isHaveStatistic(), argsExecutive.isFullStatistic());
        State floatState = new NumberState(argsExecutive.isHaveStatistic(), argsExecutive.isFullStatistic());
        State stringState = new StringState(argsExecutive.isHaveStatistic(), argsExecutive.isFullStatistic());


        for (String file : argsExecutive.getFiles()){
            fileExecutive.filterFile(file);
        }
        fileExecutive.deleteFileIfEmpty();


        integerState.analysisAndPrint(fileExecutive.getIntegersFile());
        floatState.analysisAndPrint(fileExecutive.getFloatsFile());
        stringState.analysisAndPrint(fileExecutive.getStringsFile());
    }
}