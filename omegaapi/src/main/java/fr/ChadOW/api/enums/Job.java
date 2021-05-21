package fr.ChadOW.api.enums;

public enum Job {
    NONE("§6Chômeur", 0),
    MINOR("§6Mineur", 100),
    LUMBERJACK("§6Bucheron", 100),
    TERRAFORMER("§6Terraformeur", 100),
    HUNTER("§6Chasseur", 100),
    FARMER("§6Fermier", 100),
    BAKER("§6Enfourneur", 100),
    FISHER("§6Pêcheur", 100),
    COOK("§6Cuistot", 100),
    ALCHEMIST("§6Alchimiste", 100);

    private static final float expMultiplicator = 1.2f;
    private static final float moneyMultiplicator = 1.05f;

    public static float getExpMultiplicator() {
        return expMultiplicator;
    }

    public static float getMoneyMultiplicator() {
        return moneyMultiplicator;
    }


    private final String name;
    private final int base;

    Job(String name, int base) {
        this.name = name;
        this.base = base;
    }

    public static Job getDefaultJob() {
        return NONE;
    }

    public String getName() {
        return name;
    }

    public int getBase() {
        return base;
    }
}
