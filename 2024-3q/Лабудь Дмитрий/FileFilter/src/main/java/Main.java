import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {


        FileFilter fileFilter = new FileFilter();
        fileFilter.readArguments(args);
        System.out.println("----------Done! Program finish!----------");
    }
}