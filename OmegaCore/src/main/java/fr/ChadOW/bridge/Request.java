package fr.ChadOW.bridge;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import java.util.List;

public class Request implements Bridgeable {
    private static int counter = 0;
    private RequestRunnable runnable;
    private String ID;
    private List<Object> data;

    public Request(RequestRunnable runnable, List<Object> data) {
        this.ID = "request_" + counter;
        this.runnable = runnable;
        this.data = data;
        counter++;
    }

    public String getID() {
        return ID;
    }

    public RequestRunnable getRunnable() {
        return runnable;
    }

    public void setRunnable(RequestRunnable runnable) {
        this.runnable = runnable;
    }

    public Object[] getData() {
        return data.toArray(new Object[0]);
    }

    public ByteArrayDataOutput createBytes() {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF(getID());
        for (Object data : getData()) {
            if (data instanceof String)
                out.writeUTF((String) data);
            if (data instanceof Character)
                out.writeChar((char) data);
            if (data instanceof Integer)
                out.writeInt((int) data);
            if (data instanceof Double)
                out.writeDouble((double) data);
            if (data instanceof Long)
                out.writeLong((long) data);
            if (data instanceof Float)
                out.writeFloat((float) data);
        }
        out.writeUTF("END");

        return out;
    }
}
