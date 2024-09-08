package org.egorsemenovv.console;

import lombok.Getter;

@Getter
public class ConsoleArgsParser {

    private static final ConsoleArgsParser INSTANCE = new ConsoleArgsParser();
    private static final String PATH = "-o";
    private static final String PREFIX = "-p";

    private final ConsoleArgs consoleArgs;

    public ConsoleArgsParser(){
        this.consoleArgs = new ConsoleArgs();
    }

    public void parseArgs(String[] args) {
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if(arg.startsWith("-")){
                consoleArgs.addOption(arg, true);
                if (i < args.length-1 && !args[i+1].startsWith("-")){
                    if (args[i].equals(PATH)){
                        consoleArgs.setPath(args[++i]);
                    } else if (args[i].equals(PREFIX)) {
                        consoleArgs.setPrefix(args[++i]);
                    }
                }
            }
            else {
                consoleArgs.addFileName(arg);
            }
        }
    }

}
