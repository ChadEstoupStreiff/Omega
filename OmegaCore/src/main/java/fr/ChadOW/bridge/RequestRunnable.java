package fr.ChadOW.bridge;

import java.util.List;

public interface RequestRunnable {
    void run(Request request, List<String> data);
}
