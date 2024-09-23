package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Тестовое задание для Korona Tech
 * @author Мельников В.О
 **/
public class App 
{
    public static void main(String[] args)
    {
        final String INTEGERS_FILENAME = "integers.txt";
        final String FLOATS_FILENAME = "floats.txt";
        final String STRINGS_FILENAME="strings.txt";

        boolean pChecked = false,
                sChecked = false,
                oChecked = false,
                aChecked = false,
                fChecked = false;
        String prefix = "";
        String directoryPath = "";
        ArrayList<String> inputFilesPaths = new ArrayList<String>();
        ArrayList<String> inputStrings = new ArrayList<String>();

        //обработка списка аргументов
        for(int i =0; i<args.length; i++)
        {
            boolean isParam = true;
            switch (args[i]) {
                case "-s": sChecked = true; break;
                case "-p": pChecked = true;
                    prefix=( (i+1)<args.length ? args[++i] : "");
                    break;
                case "-o": oChecked = true;
                    directoryPath=( (i+1)<args.length ? args[++i] : "");
                    break;
                case "-f": fChecked = true; break;
                case "-a": aChecked = true; break;
                default: isParam = false;
            }
            if (!isParam)
            {
                inputFilesPaths.add(args[i]);
            }
        }

        //считывание информации из файлов
        for(String str: inputFilesPaths)
        {
            Path pathStr = Path.of(str);
            if(!Files.exists(pathStr))
            {
                System.out.println("файл " + str + " НЕ существует");
                continue;
            }
            try {
                List<String> fileContainment = Files.readAllLines(pathStr);
                inputStrings.addAll(fileContainment);
            } catch (IOException ex) {
                System.out.println("Ошибка при открытии файла " + str + " : " + ex.getMessage());
            }
        }

        //анализ считанных строк
        IntegerCollection intCollection = new IntegerCollection();
        FloatCollection floatCollection = new FloatCollection();
        StringCollection stringCollection = new StringCollection();

        for (String str : inputStrings) {
            if(!intCollection.ManageString(str)){
                if(!floatCollection.ManageString(str)){
                    stringCollection.ManageString(str);
                }
            }
        }

        //запись результатов в файлы

        //Проверка существования и создание указанного каталога
        PathManager pm = new PathManager(directoryPath, prefix);
        pm.SecureDestinationDirectory();

        if(!intCollection.writeToFile ( pm.assembleFullPath (INTEGERS_FILENAME), aChecked) ){
            System.out.println("Записать в файл \"" + INTEGERS_FILENAME + "\" не удалось" );
        }
        if(!floatCollection.writeToFile ( pm.assembleFullPath (FLOATS_FILENAME), aChecked) ){
            System.out.println("Записать в файл \"" + FLOATS_FILENAME + "\" не удалось" );
        }
        if(!stringCollection.writeToFile ( pm.assembleFullPath (STRINGS_FILENAME), aChecked) ){
            System.out.println("Записать в файл \"" + STRINGS_FILENAME + "\" не удалось" );
        }

        //Вывод статистики
        ICollectStatistics[] sortedData = {
                (ICollectStatistics) intCollection,
                (ICollectStatistics) floatCollection,
                (ICollectStatistics) stringCollection
        };
        printStatistics(sortedData, sChecked, fChecked);

    }

    private static String assembleFullPath(String dPath, String prefix, String fileName) {
        return dPath +'\\'+ prefix + fileName;
    }

    private static void printStatistics(ICollectStatistics[] collectionsArray, boolean showShortFlag, boolean showFullFlag) {
        for (ICollectStatistics ic: collectionsArray){
            if(showShortFlag){
                System.out.println( ic.getShortStatistics() );
            }
            if (showFullFlag){
                System.out.println( ic.getFullStatistics() );
            }
        }
    }
}
