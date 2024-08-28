package by.kozlov;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import by.kozlov.parser.MyParser;
import by.kozlov.service.MyService;
import by.kozlov.service.MyServiceDoubleImpl;
import by.kozlov.service.MyServiceIntImpl;
import by.kozlov.service.MyServiceStringImpl;
import by.kozlov.writer.MyWriterImpl;
import by.kozlov.writer.Writable;

public class App {

    static final String pathInt = "integers.txt";
    static final String pathFloat = "floats.txt";
    static final String pathString = "strings.txt";

    public static void main( String[] args ) {

        MyParser myParser = new MyParser();
        List<String> inputFiles = myParser.parse(args);
        String savePath = myParser.getSavePath();
        String preName = myParser.getPreName();
        boolean isAppend = myParser.isAppend();
        boolean isMinStat = myParser.isMinStat();
        boolean isFullStat = myParser.isFullStat();
        Writable writer = new MyWriterImpl();
        App runner = new App();

        List<MyService> services = new ArrayList<>();
        services.add(new MyServiceIntImpl(preName+pathInt));
        services.add(new MyServiceDoubleImpl(preName+pathFloat));
        services.add(new MyServiceStringImpl(preName+pathString));

        for (String s: inputFiles) {
            try {
                List<String> lines = Files.readAllLines(Paths.get(s),StandardCharsets.UTF_8);
                for (MyService service: services) {
                    List<String> result = service.finder(lines);
                    if(!result.isEmpty()) {
                        FileWriter fileWriter = runner.getMyFileWriter(savePath,service.getNameOfSave(),isAppend);
                        writer.write(result,fileWriter);
                    }
                }
            } catch (IOException e) {
                System.out.println("no file in directory - " + e.getLocalizedMessage());
            }
        }

        if(isMinStat) {
            System.out.println();
            for (MyService service: services) {
                service.minStat();
            }
        }

        if(isFullStat) {
            System.out.println();
            for(MyService service: services) {
                service.maxStat();
            }
        }
    }

    public FileWriter getMyFileWriter(String savePath, String nameOfFile, boolean isAppend) {
        File directory = new File(savePath);
        File output = new File(directory,nameOfFile);
        FileWriter fw = null;
        if (savePath.equals(".")) {
            try {
                fw = new FileWriter(output, StandardCharsets.UTF_8,isAppend);
            } catch (IOException e) {
                System.out.println(e.getLocalizedMessage());
            }
        } else {
            if (directory.exists() || directory.mkdirs()) {
                try {
                    fw = new FileWriter(output,StandardCharsets.UTF_8,isAppend);
                } catch (IOException e) {
                    System.out.println(e.getLocalizedMessage());
                }
            } else {
                System.out.println("Don`t create new directory!");
            }
        }
        return fw;
    }
}
