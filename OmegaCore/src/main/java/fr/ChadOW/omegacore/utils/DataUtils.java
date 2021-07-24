package fr.ChadOW.omegacore.utils;

import fr.ChadOW.omegacore.P;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DataUtils {
    public FileReader getFileReader(String URL) throws FileNotFoundException {
        return new FileReader(P.getInstance().getDataFolder().getPath() + "/data/" + URL);
    }

    public String readFile(String URL) throws FileNotFoundException {
        return readFile(getFileReader(URL));
    }

    public String readFile(FileReader file) {
        StringBuilder result = new StringBuilder();
        try {
            int r = file.read();
            while(r != -1) {
                result.append((char) r);
                r = file.read();
            }
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
    public FileWriter getFileWriter(String URL) throws IOException {
        return new FileWriter(P.getInstance().getDataFolder().getPath() + "/data/" + URL);
    }

    public void writeFile(String URL, String data) throws IOException {
        FileWriter file = getFileWriter(URL);
        writeFile(file, data);
        file.close();
    }

    public void writeFile(FileWriter file, String data) throws IOException {
        file.write(data);
    }
}
