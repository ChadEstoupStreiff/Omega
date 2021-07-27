package fr.ChadOW.omegacore.claim;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class OmegaChunk {

    private final World world;
    private final int x, y;
    private String ownerID;

    OmegaChunk(World world, int x, int y) {
        this.world = world;
        this.x = x;
        this.y = y;
    }

    public World getWorld() {
        return world;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }


    @Override
    public String toString() {
        return "Monde: " + Bukkit.getServer().getName() + ":" + world.getName() + ", X: " + getX() + ", Y: " + getY() + ", Propriétaire: " + getOwnerID();
    }
}
