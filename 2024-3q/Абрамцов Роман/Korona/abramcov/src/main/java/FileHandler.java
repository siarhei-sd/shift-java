import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileHandler {

    private final String outputPath;
    private final String prefix;
    private final boolean mode;
    private final Map<DataType, BufferedWriter> writers = new HashMap<>();

    public FileHandler(String outputPath, String prefix, boolean mode){
        this.outputPath = outputPath == null ? "." : outputPath;
        this.prefix = prefix == null ? "" : prefix;
        this.mode = mode;
    }

    // метод записи данных
    public void writeData(DataType dataType, String data) throws IOException {
        BufferedWriter writer = getWriterForType(dataType);
        writer.write(data);
        writer.newLine();
        writer.flush();
    }

    // обработка bufferedWriter
    private BufferedWriter getWriterForType(DataType dataType) throws IOException{
        if (!writers.containsKey(dataType)){
            String filename = generateFileName(dataType);
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename, mode));
            writers.put(dataType, writer);
        }
        return writers.get(dataType);
    }

    // метод генерирования имени файла
    private String generateFileName(DataType dataType){
        String fileName = outputPath + File.separator + prefix;
        switch (dataType){
            case FLOAT:
                fileName += "float.txt";
            break;
            case INTEGER:
                fileName += "integers.txt";
            break;
            case STRING:
                fileName += "string.txt";
            break;
        }
        return fileName;
    }

    // метод закрытия файлов
    public void closeWriters() {
        for (BufferedWriter writer : writers.values()){
            try{
                writer.close();
            }
            catch (IOException e){
                System.err.println("Ошибка закрытия" + e.getMessage());
            }
        }
    }
}
