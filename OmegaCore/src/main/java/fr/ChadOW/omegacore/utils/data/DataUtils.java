package fr.ChadOW.omegacore.utils.data;

import fr.ChadOW.api.managers.JedisManager;
import fr.ChadOW.omegacore.P;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataUtils<T> {

    public void saveData(String URL, List<T> objects) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object obj : objects) {
            stringBuilder.append(JedisManager.getGson().toJson(obj));
            stringBuilder.append(";");
        }

        try {
            writeFile(URL, stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<T> readData(String URL, Type type) {
        String file = "";
        try {
            file = readFile(URL);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<T> list = new ArrayList<>();
        if (file.length() > 0) {
            for (String str : file.split(";")) {
                list.add(JedisManager.getGson().fromJson(str, type));
            }
        }
        return list;
    }

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
