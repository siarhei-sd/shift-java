package by.kozlov.writer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MyWriterImpl implements Writable{

    @Override
    public void write(List<String> args, FileWriter fw) {
        try(BufferedWriter bw = new BufferedWriter(fw)) {
            for (String s: args) {
                bw.write(s);
                bw.newLine();
                bw.flush();
            }
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
