import ru.korona.service.FileService;
import ru.korona.service.impl.DefaultFileService;


public class DataSeparator {

    public static void main(String[] args) {
        FileService fileService = new DefaultFileService();
        fileService.processFiles(args);
    }
}
