package fr.ChadOW.omegacore.world;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public enum Spawn {
    LIBRE("libre", 0, 100, 0),
    LIBRE_NETHER("libre_nether", 0, 100, 0),
    LIBRE_END("libre_the_end", 0, 100, 0),
    RESSOURCE("ressource", 0, 100, 0),
    RESSOURCE_NETHER("ressource_nether", 0, 100, 0),
    RESSOURCE_END("ressource_the_end", 0, 100, 0);

    private Location location;

    private final String world;
    private final int x,y,z;

    Spawn(String world, int x, int y, int z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String getWorldName() {
        return world;
    }

    public static void updateLocation() {
        for (Spawn spawn : values()) {
            spawn.location = new Location(Bukkit.getWorld(spawn.world), spawn.x, spawn.y, spawn.z);
        }
    }

    public Location getLocation() {
        return location;
    }
}
