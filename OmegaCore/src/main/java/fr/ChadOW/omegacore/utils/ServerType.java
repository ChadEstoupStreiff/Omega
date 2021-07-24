package fr.ChadOW.omegacore.utils;

import fr.ChadOW.omegacore.P;

public enum ServerType {
    NORMAL("free"),
    RESSOURCES("ressources"),
    WORLDS("worlds"),
    EVENT("event");

    public static void init(P p) {
        serverType = p.getConfig().getString("server.type");
        P.getInstance().getSender().sendMessage("Server type: " + serverType);
    }

    private final String type;

    ServerType(String type) {
        this.type = type;
    }

    public static String serverType;

    public static String getServerType() {
        return serverType;
    }

    public static boolean equals(String type) {
        return serverType.equals(type);
    }

    public static boolean equals(ServerType type) {
        return equals(type.type);
    }

    @Override
    public String toString() {
        return type;
    }
}
