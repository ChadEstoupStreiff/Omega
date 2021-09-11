package fr.ChadOW.bridge;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import java.util.List;

public class RequestAnswer implements Bridgeable {
    private String ID;
    private List<Object> data;

    public RequestAnswer(Request request, List<Object> data) {
        this.ID = "answer_" + request.getID();
        this.data = data;
    }

    public String getID() {
        return ID;
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
