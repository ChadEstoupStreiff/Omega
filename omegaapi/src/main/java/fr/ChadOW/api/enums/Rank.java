package fr.ChadOW.api.enums;

import java.util.UUID;

public enum Rank {
    DEFAULT("§7", "§7[J] §f", "§4§lSALE PUTE >> §7", " §8> §f", 0, 0),
    LEGEND("§e", "§e[L] §f", "§e[Legend] §f", " §8> §f", 1, 0),
    MYTH("§6", "§6[M] §f", "§6[Mythique] §f", " §8> §f", 2, 0),
    OLAF("§5", "§5[O] §f", "§5[Olaf] §f", " §8> §f", 3, 0),
    HELPER("§a", "§a[H] §f", "§a[Helpeur] §f", " §8> §f", 1, 1),
    MOD("§b", "§b[M] §f", "§b[Modérateur] §f", " §8> §f", 1, 2),
    BUILDER("§a", "§a[B] §f", "§a[Buildeur] §f", " §8> §f", 1, 1),
    DEV("§b", "§b[D] §f", "§b[Développeur] §f", " §8> §f", 9, 9),
    ADMIN("§4", "§4[A] §f", "§4[Administrateur] §f", " §8> §f", 10, 10),
    YTB("§f", "§c[Y] §f", "§f[You§4Tubeur§f] §f", " §8> §f", 3, 0);

    private final String color;
    private final String tab;
    private final String prefix;
    private final String suffix;
    private final int power;
    private final int staffPower;


    Rank(String color, String tab, String prefix, String suffix, int power, int staffPower) {
        this.color = color;
        this.tab = tab;
        this.prefix = prefix;
        this.suffix = suffix;
        this.power = power;
        this.staffPower = staffPower;
    }

    public static boolean isExisting(String value) {
        for (Rank rank : values()) {
            if (rank.toString().equalsIgnoreCase(value))
                return true;
        }
        return false;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public int getPower() {
        return power;
    }

    public int getStaffPower() {
        return staffPower;
    }

    public String getColor() {
        return color;
    }

    public String getTab() {
        return tab;
    }

    public boolean hasPower(UUID uuid, int power) {
        return getPower() >= power;
    }

    public boolean hasStaffPower(UUID uuid, int power) {
        return getStaffPower() >= power;
    }

    public static Rank getDefaultRank() {
        return DEFAULT;
    }
}
