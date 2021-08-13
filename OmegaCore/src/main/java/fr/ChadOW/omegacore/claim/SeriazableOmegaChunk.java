package fr.ChadOW.omegacore.claim;

import org.bukkit.Bukkit;

public class SeriazableOmegaChunk {

    private final int x;
    private final int y;
    private final String world;
    private final String ownerID;

    public SeriazableOmegaChunk(OmegaChunk omegaChunk) {
        x = omegaChunk.getX();
        y = omegaChunk.getY();
        world = omegaChunk.getWorld().getName();
        ownerID = omegaChunk.getOwnerID();
    }

    public void createOmegaChunk(ClaimManager claimManager) {
        OmegaChunk omegaChunk = claimManager.getChunk(Bukkit.getWorld(world), x, y);
        omegaChunk.setOwnerID(ownerID);
    }
}
