package fr.ChadOW.omegacore.utils.hologram;

import com.google.common.collect.Maps;
import fr.ChadOW.omegacore.P;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

public class Hologram {

    public static void init(P p) {
        p.getCommand("hologram").setExecutor(new CommandHologram());
    }

    private LinkedList<ArmorStand> armorStands;
    private ArmorStand mainArmorStand;
    private String customName;
    private boolean small, marker, arms, baseplate, visible;
    private HologramLineListener hologramLineListener;

    /**
     *
     * The Constructor of the class
     *
     * @param customName
     */
    public Hologram(final String customName){
        this.customName = customName.replace("&", "§");
        this.small = false;
        this.marker = false;
        this.arms = false;
        this.baseplate = true;
        this.visible = true;
        this.mainArmorStand = null;
        this.armorStands = new LinkedList<>();
        this.hologramLineListener = null;
    }


    public enum LineDirection {
        UP,
        DOWN;
    }

    /**
     *
     * Basically add line depending on the position
     * of another {@link Hologram}
     *
     * @param line
     * @param lineDirection
     * @return new instance of {@link Hologram}
     */
    public Map<Hologram, Location> addLine(final String line, final LineDirection lineDirection, final HologramLineListener hologramLineListener){
        final Location originalLocation = mainArmorStand.getLocation();
        final Location adjustLocation = armorStands.get(armorStands.size() - 1).getLocation();
        switch (lineDirection){
            default:
            case UP:
                adjustLocation.setY(originalLocation.getY() + armorStands.size());
            case DOWN:
                adjustLocation.setY(originalLocation.getY() - armorStands.size());
        }

        final Map<Hologram, Location> map = Maps.newHashMap();
        map.put(hologramLineListener.onAdd(new Hologram(line)), adjustLocation);

        return map;
    }

    public Hologram addSimpleLine(final String line, final LineDirection lineDirection){
        final Map<Hologram, Location> newLine = this.addLine(line, lineDirection, (hologram -> {
            hologram.setVisible(false)
                    .setBaseplate(false)
                    .setArms(false)
                    .setMarker(false);

            return hologram;
        }));
        final Hologram hologram = newLine.entrySet().stream().collect(Collectors.toList()).get(0).getKey();
        hologram.hologramLineListener.onAdd(hologram).spawn(newLine.entrySet().stream().collect(Collectors.toList()).get(0).getValue());
        
        return this;
    }

    /**
     *
     * Create the ArmorStand
     * (Don't add lines if it's not spawned)
     *
     * @param loc
     * @return new instance of {@link ArmorStand}
     */
    public ArmorStand spawn(final Location loc){
        final ArmorStand armorStand = loc.getWorld().spawn(loc, ArmorStand.class);

        armorStand.setCustomName(customName);
        armorStand.setSmall(small);
        armorStand.setMarker(marker);
        armorStand.setArms(arms);
        armorStand.setBasePlate(baseplate);
        armorStand.setVisible(visible);

        this.mainArmorStand = armorStand;
        this.armorStands.add(armorStand);
        return armorStand;
    }

    /**
     * GETTERS
     */
    public ArmorStand getMainArmorStand() {
        return mainArmorStand;
    }

    public String getCustomName() {
        return customName;
    }

    public boolean isSmall() {
        return small;
    }

    public boolean hasMarker() {
        return marker;
    }

    public boolean hasArms() {
        return arms;
    }

    public boolean hasBaseplate() {
        return baseplate;
    }

    public boolean isVisible() {
        return visible;
    }

    /**
     * SETTERS
     */
    public Hologram setCustomName(String customName) {
        this.customName = customName;
        return this;
    }

    public Hologram setSmall(boolean small) {
        this.small = small;
        return this;
    }

    public Hologram setMarker(boolean marker) {
        this.marker = marker;
        return this;
    }

    public Hologram setArms(boolean arms) {
        this.arms = arms;
        return this;
    }

    public Hologram setBaseplate(boolean baseplate) {
        this.baseplate = baseplate;
        return this;
    }

    public Hologram setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }
}
