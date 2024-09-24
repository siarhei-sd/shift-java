package org.example;


public class ParamsParser {

    public static void parseArgs(String[] args) throws IllegalParametersCombinationException {
        for(int i = 0; i <args.length; i++) {
            if(args[i].equals("-s")) Parameters.setShortStats(true);
            if(args[i].equals("-f")) Parameters.setShortStats(false);
            if(args[i].equals("-p")) {
                if(i < args.length - 1 && !args[i+1].startsWith("-")) {
                    Parameters.setPrefix(args[i+1]);
                } else {
                    throw new IllegalParametersCombinationException("Не указан префикс для названия файлов");
                }
            }
            if(args[i].equals("-a")) Parameters.setAddToFile(true);
            if(args[i].equals("-o")) {
                if(i < args.length - 1 && FileParser.isValidPath(args[i+1])) {
                    Parameters.setDestinationfolder(args[i+1]);
                } else {
                    throw new IllegalParametersCombinationException("Не корректный адрес для сохранения файлов");
                }

            }
            if(args[i].endsWith(".txt")) Parameters.addFileToRead(args[i]);
        }
    }


}
