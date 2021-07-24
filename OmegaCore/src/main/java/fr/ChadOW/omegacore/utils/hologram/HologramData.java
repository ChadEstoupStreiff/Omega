package fr.ChadOW.omegacore.utils.hologram;

import fr.ChadOW.api.managers.JedisManager;
import fr.ChadOW.omegacore.P;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.List;

public class HologramData {

    private String name, world;
    private double x, y, z;
    private List<String> lines;


    public HologramData(Hologram hologram) {
        name = hologram.getName();
        world = hologram.getLocation().getWorld().getName();
        x = hologram.getLocation().getX();
        y = hologram.getLocation().getY();
        z = hologram.getLocation().getZ();
        lines = hologram.getLinesAsStrings();
    }

    public Hologram createHologram() {
        return P.getInstance().getHologramManager().createHologram(name, new Location(Bukkit.getWorld(world), x, y, z), lines);
    }

    @Override
    public String toString() {
        return JedisManager.getGson().toJson(this);
    }
}
