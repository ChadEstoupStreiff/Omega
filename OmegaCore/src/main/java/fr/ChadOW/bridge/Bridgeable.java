package fr.ChadOW.bridge;

import com.google.common.io.ByteArrayDataOutput;

public interface Bridgeable {

    ByteArrayDataOutput createBytes();
}
