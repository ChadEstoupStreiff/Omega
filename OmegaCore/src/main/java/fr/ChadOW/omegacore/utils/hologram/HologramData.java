package fr.ChadOW.omegacore.utils.hologram;

import fr.ChadOW.api.managers.JedisManager;
import fr.ChadOW.omegacore.P;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.List;
import java.util.Objects;

public class HologramData {

    private final String name;
    private final String world;
    private final double x;
    private final double y;
    private final double z;
    private final List<String> lines;


    public HologramData(Hologram hologram) {
        name = hologram.getName();
        world = Objects.requireNonNull(hologram.getLocation().getWorld()).getName();
        x = hologram.getLocation().getX();
        y = hologram.getLocation().getY();
        z = hologram.getLocation().getZ();
        lines = hologram.getLinesAsStrings();
    }

    public Hologram createHologram() {
        return createHologram(P.getInstance().getHologramManager());
    }

    public Hologram createHologram(HologramManager hologramManager) {
        return hologramManager.createHologram(name, new Location(Bukkit.getWorld(world), x, y, z), lines);
    }

    public String getName() {
        return name;
    }

    public String getWorld() {
        return world;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public List<String> getLines() {
        return lines;
    }

    @Override
    public String toString() {
        return JedisManager.getGson().toJson(this);
    }
}
