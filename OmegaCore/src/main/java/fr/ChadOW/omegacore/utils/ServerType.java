package fr.ChadOW.omegacore.utils;

import org.bukkit.Bukkit;

public enum ServerType {
    NORMAL("normal"),
    RESSOURCES("ressources"),
    WORLDS("worlds"),
    EVENT("event");

    private String type;

    ServerType(String type) {
        this.type = type;
    }

    public static String serverType;

    public static boolean equals(String type) {
        if (serverType == null)
            serverType = Bukkit.getServer().getName();
        return serverType.equals(type);
    }

    public static boolean equals(ServerType type) {
        return equals(type.type);
    }
}
