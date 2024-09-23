package org.example;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FloatCollection  extends BaseCollection implements ICollectStatistics{
    private ArrayList<Double> numbers;
    private double min, max, sum;

    public FloatCollection() {
        super ();
        numbers = new ArrayList<Double>();
    }

    @Override
    public boolean ManageString(String str) {
        try{
            Double num = Double.parseDouble(str);
            numbers.add(num);
            sources.add(str);
            return true;
        }
        catch (NumberFormatException nfe) {
            return false;
        }
    }

    @Override
    public boolean writeToFile(String path, boolean appendFlag) {

        //Требование не создавать пустой файл!
        if (numbers.isEmpty()){
            return true;
        }
        //
        try (FileWriter fWriter = new FileWriter(path, appendFlag)){
            min = numbers.get(0);
            max = numbers.get(0);
            sum = 0;
            super.writeToFile(path, appendFlag);

            double nd;
            for (int i= 0; i<sources.size(); i++)
            {
                nd = numbers.get(i);
                fWriter.write(sources.get(i)+'\n');
                min = (nd < min) ? nd : min;
                max = (nd > max) ? nd : max;
                sum += nd;
                count++;
            }
            return true;
        }
        catch (IOException ex) {
            return false;
        }
    }

    @Override
    public String getFullStatistics(){
        String result;
        if (!wasSaved){
            result= "Запись в файл не производилась";
        }
        else {
            result = getShortStatistics() +
                    "; Min " + min +
                    "; Max " + max +
                    "; Sum " + sum +
                    "; Avg " + (sum / count);
        }
        return result;
    }

}
