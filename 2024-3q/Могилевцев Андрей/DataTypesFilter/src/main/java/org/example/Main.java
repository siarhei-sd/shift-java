package org.example;

public class Main {

    public static void main(String[] args) {
        try {
            parseArgs(args);
            readFiles();
            MemoryRepository.saveResults();
            showStats();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void parseArgs(String[] args) throws IllegalParametersCombinationException {
        ParamsParser.parseArgs(args);
    }

    public static void readFiles() throws ReadFileException{
        FileParser fileParser = new FileParser();
        if(!Parameters.getFilesToRead().isEmpty()) {
            for (String file : Parameters.getFilesToRead()) {
                fileParser.readFile(file);
            }
        } else {
            throw new ReadFileException("Файл не может быть прочитан или не указаны файлы для чтения");
        }
    }

    public static void showStats() {
        if(Parameters.isShortStats()) MemoryRepository.printShortStatistics();
        else MemoryRepository.printFullStatistics();
    }
    /*public static String[] getCorrectArgs() {
        System.out.println("Введённые параметры некорректны или возникла ошибка. Введите комбинацию параметров для запуска:" );
        printParamsDescription();
        Scanner s = new Scanner(System.in);
        String line = s.nextLine();
        return line.split(" ");
    }

    public static void printParamsDescription() {
        System.out.println("с помощью параметра -p можно задать префикс для имени файлов с результатами");
        System.out.println("с помощью параметра -o можно задать путь для сохранения файлов с результатами");
        System.out.println("если указан параметр -s будет выведена краткая статистика ");
        System.out.println("если указан параметр -f будет выведена полная статистика ");
        System.out.println("если указан параметр -a, то новые результаты добавляются в файлы с предыдущими");

    }*/
}