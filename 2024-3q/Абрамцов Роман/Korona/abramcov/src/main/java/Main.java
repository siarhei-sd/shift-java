import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try{
            CmdParser parser = new CmdParser(args);

            if (!parser.isValid()) {
                System.err.println("Ошибка: Не указаны входные файлы.");
                return;
            }

            // Сбор входных файлов
            List<File> inputFiles = new ArrayList<>();
            for (String fileName : parser.getInputFiles()) {
                File file = new File(fileName);
                if (file.exists()) {
                    inputFiles.add(file);
                } else {
                    System.err.println("Файл не найден: " + fileName);
                }
            }

            FileHandler fileHandler = new FileHandler(parser.getOutputDirectory(), parser.getPrefix(), parser.isMode());
            Statistics statistics = new Statistics(parser.isFullStats());

            DataFilter dataFilter = new DataFilter(fileHandler, statistics);

            dataFilter.dataFilter(inputFiles);

            statistics.printStats();

            fileHandler.closeWriters();
        }
        catch(Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
