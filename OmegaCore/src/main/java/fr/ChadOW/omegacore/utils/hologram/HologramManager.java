package fr.ChadOW.omegacore.utils.hologram;

import fr.ChadOW.omegacore.P;
import fr.ChadOW.omegacore.utils.DataUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HologramManager {
    private final String dataPath = "holograms.dat";
    private final List<Hologram> holograms = new ArrayList<>();

    public HologramManager(P p) {
        loadHolograms();

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

    private void loadHolograms() {
        P.getInstance().getSender().sendMessage("[Hologram] Loading ...");
        List<SerializableHologram> hologramData = new DataUtils<SerializableHologram>().readData(dataPath, SerializableHologram.class);
        hologramData.forEach(hologram -> hologram.createHologram(this));
    }

    public void saveHolograms() {
        P.getInstance().getSender().sendMessage("[Hologram] Saving ...");
        List<SerializableHologram> hologramData = new ArrayList<>();
        new ArrayList<>(holograms).forEach(hologram -> {
            hologramData.add(new SerializableHologram(hologram));
            deleteHologram(hologram);
        });
        new DataUtils<SerializableHologram>().saveData(dataPath, hologramData);
    }
}
