package org.example;
import java.util.ArrayList;

public class BaseCollection {
    //строки хранятся чтобы записать их в результирующий файл ровно в том виде,
    // в котором они были прочитаны ("00000" не стало "0")
    protected ArrayList<String> sources;
    protected int count;
    protected String lastPath;
    protected boolean wasSaved;

    public BaseCollection() {
        sources= new ArrayList<>();
    }

    public boolean ManageString(String str) {
        return true;
    }

    //Запись строк в файл, возвращает true в случае успеха, false в случае ошибки
    public boolean writeToFile(String path, boolean appendFlag) {
        wasSaved= true;
        lastPath = path;
        count = 0;
        return true;
    }

    public String getShortStatistics() {
        String result;
        if (!wasSaved){
            result= "Запись в файл не производилась";
        }
        else {
            result = lastPath + ": Count " + count;
        }
        return result;
    }
}
