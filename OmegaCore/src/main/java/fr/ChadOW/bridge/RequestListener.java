package fr.ChadOW.bridge;

import java.util.List;

public class RequestListener {
    private List<String> firstData;
    private Runnable runnable;

    public RequestListener(List<String> firstData, Runnable runnable) {
        this.firstData = firstData;
        this.runnable = runnable;
    }

    public List<String> getFirstData() {
        return firstData;
    }

    public void setFirstData(List<String> firstData) {
        this.firstData = firstData;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public void tryRun(List<String> data) {
        if (data.size() >= firstData.size()) {
            for (int i = 0; i < firstData.size(); i++) {
                if (!firstData.get(i).equals(data.get(i)))
                    return;
            }
            getRunnable().run();
        }
    }
}
