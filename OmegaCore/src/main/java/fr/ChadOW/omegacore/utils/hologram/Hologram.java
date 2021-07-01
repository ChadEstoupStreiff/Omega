package fr.ChadOW.omegacore.utils.hologram;

import fr.ChadOW.api.managers.JedisManager;
import fr.ChadOW.omegacore.P;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Hologram {

    private static final String dataPath = "data/holograms.txt";

    public static void init(P p) {
        //TODO a test
        String str = "";
        try {
            FileReader file = new FileReader(dataPath);
            int ch = file.read();
            while(ch != -1) {
                str += (char)ch;
                file.close();
            }
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] datas = str.split(";");
        for (String data : datas) {
            JedisManager.getGson().fromJson(data, HologramData.class).fixGSON().createHologram();
        }

        p.getCommand("hologram").setExecutor(new CommandHologram());
        Bukkit.getScheduler().runTaskTimer(P.getInstance(), () -> {
            for (Hologram hologram : holograms)
                hologram.update();
        }, 20, 20);
    }

    public static void disable(P p) {
        String str = "";
        for (Hologram hologram : holograms) {
            str += hologram.toString() + ';';
        }
        if (str.length() > 0)
            str = str.substring(0, str.length() -1);

        //TODO à vérifier
        try {
            FileWriter file = new FileWriter(dataPath);
            file.write(str);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final List<Hologram> holograms = new ArrayList<>();

    public static Hologram getHologram(String name) {
        for (Hologram hologram : holograms) {
            if (hologram.getName().equals(name))
                return hologram;
        }
        return null;
    }


    private String name;
    private boolean permanent;
    private Location location;
    private final ArrayList<ArmorStand> lines = new ArrayList<>();

    public Hologram(String name, Location location) {
        this.name = name;
        this.location = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ());
        this.permanent = true;
        holograms.add(this);
    }

    public Hologram(String name, Location location, Collection<String> lines) {
        this(name, location);
        addLines(lines);
    }

    public Hologram removeLine(int i) {
        if (i > 0 && i < lines.size()) {
            lines.get(i).remove();
            lines.remove(i);
        }
        updateLocation();
        return this;
    }

    public Hologram addLine(String line) {
        setLineAtIndex(line, line.length());
        return this;
    }

    public Hologram addLines(Collection<String> lines) {
        for (String line : lines)
            addLine(line);
        return this;
    }

    public Hologram insertLine(String line, int i) {
        if (i >= 0) {
            if (i < lines.size()) {
                ArmorStand armorStand = initArmorStand(line);
                lines.add(i, armorStand);
                updateLocation();
            } else {
                addLine(line);
            }
        }
        return this;
    }

    public Hologram setLineAtIndex(String line, int i) {
        if (i >= lines.size()) {
            ArmorStand armorStand = initArmorStand(line);
            lines.add(armorStand);
            updateLocation();
        } else if (i >= 0)
            lines.get(i).setCustomName(line);
        return this;
    }

    private ArmorStand initArmorStand(String line) {
        final ArmorStand armorStand = location.getWorld().spawn(new Location(location.getWorld(), location.getX(), location.getY() - lines.size()*.25, location.getZ()), ArmorStand.class);

        armorStand.setCustomName(line);
        armorStand.setCustomNameVisible(true);
        armorStand.setSmall(true);
        armorStand.setInvulnerable(true);
        armorStand.setMarker(false);
        armorStand.setArms(false);
        armorStand.setBasePlate(false);
        armorStand.setVisible(false);
        armorStand.setGravity(false);
        armorStand.setCanPickupItems(false);
        armorStand.setSilent(true);
        armorStand.setPersistent(true);

        return armorStand;
    }

    private void updateLocation() {
        for (int i = 0; i < lines.size(); i++) {
            lines.get(i).teleport(new Location(location.getWorld(), location.getX(), location.getY() - i * .25, location.getZ()));
        }
    }

    public void delete() {
        for (ArmorStand armorStand : lines) {
            armorStand.remove();
        }
        holograms.remove(this);
    }

    private void update() {
        //TODO placeholders
    }


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
        updateLocation();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ArmorStand> getLines() {
        return lines;
    }

    public List<String> getLinesAsStrings() {
        List<String> str = new ArrayList<>();
        for (ArmorStand armorStand : getLines()) {
            str.add(armorStand.getCustomName());
        }
        return str;
    }

    public boolean isPermanent() {
        return permanent;
    }

    public void setPermanent(boolean permanent) {
        this.permanent = permanent;
    }
}
