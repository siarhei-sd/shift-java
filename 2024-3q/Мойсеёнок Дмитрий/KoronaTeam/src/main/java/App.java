public class App
{
    public static void main( String[] args )
    {
        // Парсинг аргументов командной строки
        CommandLineParser parser = new CommandLineParser(args);
        FileProcessor fileProcessor = new FileProcessor(parser.getInputFiles(), parser.getOutputPath(), parser.getPrefix(), parser.isAppend(), parser.isFullStatistics());

        // Запуск обработки файлов
        fileProcessor.processFiles();
    }
}
