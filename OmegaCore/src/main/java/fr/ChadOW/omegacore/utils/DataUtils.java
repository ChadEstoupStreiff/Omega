package fr.ChadOW.omegacore.utils;

import fr.ChadOW.api.managers.JedisManager;
import fr.ChadOW.omegacore.P;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
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
            String[] rawData = file.split(";");
            for (String str : rawData) {
                T obj = JedisManager.getGson().fromJson(str, type);
                if (obj != null)
                    list.add(obj);
            }
        }
        return list;
    }

    public InputStreamReader getFileReader(String URL) {
        try {
            return new InputStreamReader(new FileInputStream(P.getInstance().getDataFolder().getPath() + "/data/" + URL), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String readFile(String URL) throws FileNotFoundException {
        return readFile(getFileReader(URL));
    }

    public String readFile(InputStreamReader file) {
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
    public OutputStreamWriter getFileWriter(String URL) throws IOException {
        return new OutputStreamWriter(new FileOutputStream(P.getInstance().getDataFolder().getPath() + "/data/" + URL), StandardCharsets.UTF_8);
    }

    public void writeFile(String URL, String data) throws IOException {
        OutputStreamWriter file = getFileWriter(URL);
        writeFile(file, data);
        file.close();
    }

    public void writeFile(OutputStreamWriter file, String data) throws IOException {
        file.write(data);
    }
}
