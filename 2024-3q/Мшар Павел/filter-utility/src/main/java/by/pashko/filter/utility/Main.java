package by.pashko.filter.utility;

import static by.pashko.filter.utility.Constants.APPEND_FLAG;
import static by.pashko.filter.utility.Constants.EMPTY_ARGUMENT_VALUE_ERROR_MESSAGE;
import static by.pashko.filter.utility.Constants.FULL_STAT_FLAG;
import static by.pashko.filter.utility.Constants.OUTPUT_FLAG;
import static by.pashko.filter.utility.Constants.PREFIX_FLAG;
import static by.pashko.filter.utility.Constants.SHORT_STAT_FLAG;
import static by.pashko.filter.utility.Constants.SUPPORTED_FILE_EXTENSION;
import static by.pashko.filter.utility.Constants.UNSUPPORTED_ARGUMENT_ERROR_MESSAGE;

public class Main {

    /**
     * Входная точка в программу.
     *
     * @param args Входные аргументы.
     *              <ul>Список поддерживаемых аргументов:
     *                  <li>-o -- Задает путь, где будут храниться отчеты. По умолчанию текущая директория;</li>
     *                  <li>-p -- Задает префикс имен выходных вайлов. По умолчанию отсутствует;</li>
     *                  <li>-a -- Флаг вставки в конец файла. По умолчанию файлы перезаписываются;</li>
     *                  <li>-s -- Флаг для отображения короткой статистики. По умолчанию не отображается;</li>
     *                  <li>-f -- Флаг для отображения полной статистики. По умолчанию не отображается.</li>
     *             </ul>
     */
    public static void main(String[] args) {
        ApplicationProperties applicationProperties = parseArguments(args);

        Runner runner = new Runner(applicationProperties);
        runner.run();
    }

    /**
     * Парсинг входных параметров.
     *
     * @param arguments Параметры, которые нужно парсить.
     */
    private static ApplicationProperties parseArguments(String[] arguments) {
        ApplicationProperties applicationProperties = new ApplicationProperties();

        int i = 0;

        while (i < arguments.length) {
            String argument = arguments[i];

            switch (argument) {
                case OUTPUT_FLAG:
                    if (checkIfValueExists(arguments, i)) {
                        applicationProperties.setOutputDirectory(arguments[++i]);
                    } else {
                        System.err.printf(EMPTY_ARGUMENT_VALUE_ERROR_MESSAGE, OUTPUT_FLAG);
                    }
                    break;
                case PREFIX_FLAG:
                    if (checkIfValueExists(arguments, i)) {
                        applicationProperties.setFileNamePrefix(arguments[++i]);
                    } else {
                        System.err.printf(EMPTY_ARGUMENT_VALUE_ERROR_MESSAGE, PREFIX_FLAG);
                    }
                    break;
                case APPEND_FLAG:
                    applicationProperties.setAppendToEnd(true);
                    break;
                case SHORT_STAT_FLAG:
                    applicationProperties.setShowShortStatistics(true);
                    break;
                case FULL_STAT_FLAG:
                    applicationProperties.setShowFullStatistics(true);
                    break;
                default:
                    if (argument.endsWith(SUPPORTED_FILE_EXTENSION)) {
                        applicationProperties.addInputFiles(argument);
                    } else {
                        System.err.printf(UNSUPPORTED_ARGUMENT_ERROR_MESSAGE, argument);
                    }
            }

            i++;
        }
        return applicationProperties;
    }

    private static boolean checkIfValueExists(String[] arguments, int index) {
        return arguments.length > index + 1 && !arguments[index + 1].startsWith("-");
    }
}
