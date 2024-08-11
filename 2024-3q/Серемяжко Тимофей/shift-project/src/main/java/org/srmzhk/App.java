package org.srmzhk;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.List;
import java.util.concurrent.Future;

public class App
{
    public static void main(String[] args)
    {
        try {
            CmdParser cmdParser = new CmdParser();
            cmdParser.parseCmd(args);

            char statType = cmdParser.getStatType();
            String[] outputFiles = cmdParser.getOutputFiles();
            Statistic statistic = new Statistic(outputFiles, statType);

            String[] inputFiles = cmdParser.getInputFiles();
            DataSorter dataSorter = new DataSorter(statistic);
            dataSorter.sort(inputFiles);

            List<String> integersList = dataSorter.getIntegers();
            List<String> floatsList = dataSorter.getFloats();
            List<String> stringsList = dataSorter.getStrings();
            OutputWriterTask.setAppendState(cmdParser.getAppendState());

            Future<Void> futureIntegers = null;
            Future<Void> futureFloats = null;
            Future<Void> futureStrings = null;

            //parallel output
            if (!integersList.isEmpty() || !floatsList.isEmpty() || !stringsList.isEmpty()) {
                ExecutorService executorService = Executors.newFixedThreadPool(3);
                if (!integersList.isEmpty())
                    futureIntegers = executorService.submit(new OutputWriterTask(outputFiles[0], integersList));
                if (!floatsList.isEmpty())
                    futureFloats = executorService.submit(new OutputWriterTask(outputFiles[1], floatsList));
                if (!stringsList.isEmpty())
                    futureStrings = executorService.submit(new OutputWriterTask(outputFiles[2], stringsList));
                executorService.shutdown();
            }

            try {
                if (futureIntegers != null) futureIntegers.get();
                if (futureFloats != null) futureFloats.get();
                if (futureStrings != null) futureStrings.get();
            } catch (InterruptedException | ExecutionException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }

            statistic.showStat();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}