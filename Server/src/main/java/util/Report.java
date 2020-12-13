package util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;

public class Report {
    private String filePath;
    private String data;

    public Report(String data) {
        this.data = data;
    }

    public void writeToFile(String filePath) {
        try (FileWriter writer = new FileWriter(filePath, false)) {
            writer.append(new Date().toString());
            writer.append('\n');
            writer.append(data);
            writer.append("\n\n");
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
