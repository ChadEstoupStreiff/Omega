package fr.ChadOW.omegacore.world;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public enum Spawn {
    NORMAL("world", 0, 100, 0),
    NETHER("world_nether", 0, 100, 0),
    END("world_the_end", 0, 100, 0);

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

    public Location getLocation() {
        if (location == null || !location.isWorldLoaded())
            location = new Location(Bukkit.getWorld(world), x, y, z);
        return location;
    }
}
