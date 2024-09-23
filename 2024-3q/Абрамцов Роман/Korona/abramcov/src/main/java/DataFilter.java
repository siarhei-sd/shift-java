import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class DataFilter {
    private final FileHandler fileHandler;
    private final Statistics statistics;

    public DataFilter(FileHandler fileHandler, Statistics statistics) {
        this.fileHandler = fileHandler;
        this.statistics = statistics;
    }

    private boolean isInteger(String line){
        try {
            Integer.parseInt(line);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }

    private boolean isFloat(String line){
        try{
            Float.parseFloat(line);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }
    //определение типа данных строки
    private void parseLine(String line) {
        String[] tokens = line.split("\\s+");
        StringBuilder stringBuilder = new StringBuilder(); // Для накопления строк

        for (String token : tokens) {
            token = token.trim();

            if (isInteger(token)) {
                if (!stringBuilder.isEmpty()) {
                    writeDataStats(DataType.STRING, stringBuilder.toString().trim());
                    stringBuilder.setLength(0);
                }
                int intValue = Integer.parseInt(token);
                writeDataStats(DataType.INTEGER, intValue);
            } else if (isFloat(token)) {
                if (!stringBuilder.isEmpty()) {
                    writeDataStats(DataType.STRING, stringBuilder.toString().trim());
                    stringBuilder.setLength(0);
                }

                float floatValue = Float.parseFloat(token);
                writeDataStats(DataType.FLOAT, floatValue);
            } else {
                if (!stringBuilder.isEmpty()) {
                    stringBuilder.append(" ");
                }
                stringBuilder.append(token);
            }
        }

        if (!stringBuilder.isEmpty()) {
            writeDataStats(DataType.STRING, stringBuilder.toString().trim());
        }
    }
    //чтение и разбор файла
    private void readAndParseFile(File file){
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = reader.readLine()) != null){
                parseLine(line);
            }
        }
        catch (IOException e){
            System.err.println(file.getName() + " - " + e.getMessage());
        }
    }

    public void dataFilter(List<File> inputFiles){
        for (File file : inputFiles){
            readAndParseFile(file);
        }
    }
    // запись данных и сбор статистики
    private <T> void writeDataStats(DataType dataType, T value){
        try {
            fileHandler.writeData(dataType, value.toString());
            statistics.collectStats(dataType, value);
        }
        catch (IOException e){
            System.err.println(dataType + " - " + e.getMessage());
        }
    }

}
