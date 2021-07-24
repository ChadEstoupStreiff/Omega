package fr.ChadOW.omegacore.utils.hologram;

import fr.ChadOW.omegacore.P;
import fr.ChadOW.omegacore.utils.data.DataUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HologramManager {
    private final String dataPath = "holograms.dat";
    private final List<Hologram> holograms = new ArrayList<>();

    public HologramManager(P p) {
        loadData(p);

        p.getCommand("hologram").setExecutor(new CommandHologram());
        Bukkit.getScheduler().runTaskTimer(P.getInstance(), () -> {
            for (Hologram hologram : holograms)
                hologram.update();
        }, 20, 20);
    }

    public Hologram createHologram(String name, Location location) {
        Hologram hologram = new Hologram(name, location);
        holograms.add(hologram);
        return hologram;
    }

    public Hologram createHologram(String name, Location location, Collection<String> lines) {
        Hologram hologram = new Hologram(name, location, lines);
        holograms.add(hologram);
        return hologram;
    }

    public void deleteHologram(Hologram hologram) {
        hologram.destroy();
        holograms.remove(hologram);
    }

    public Hologram getHologram(String name) {
        for (Hologram hologram : holograms) {
            if (hologram.getName().equals(name))
                return hologram;
        }
        return null;
    }

    private void loadData(P p) {
        P.getInstance().getSender().sendMessage("[Hologram] Loading ...");
        List<HologramData> hologramData = new DataUtils<HologramData>().readData(dataPath, HologramData.class);
        hologramData.forEach(hologram -> hologram.createHologram(this));
    }

    public void saveData(P p) {
        P.getInstance().getSender().sendMessage("[Hologram] Saving ...");
        List<HologramData> hologramData = new ArrayList<>();
        holograms.forEach(hologram -> hologramData.add(new HologramData(hologram)));
        new DataUtils<HologramData>().saveData(dataPath, hologramData);
    }
}
