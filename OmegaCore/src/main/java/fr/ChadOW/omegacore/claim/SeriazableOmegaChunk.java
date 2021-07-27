package fr.ChadOW.omegacore.claim;

import org.bukkit.Bukkit;

public class SeriazableOmegaChunk {

    private int x, y;
    private String world, ownerID;

    public SeriazableOmegaChunk(OmegaChunk omegaChunk) {
        x = omegaChunk.getX();
        y = omegaChunk.getY();
        world = omegaChunk.getWorld().getName();
        ownerID = omegaChunk.getOwnerID();
    }

    public OmegaChunk createOmegaChunk(ClaimManager claimManager) {
        OmegaChunk omegaChunk = claimManager.getChunk(Bukkit.getWorld(world), x, y);
        omegaChunk.setOwnerID(ownerID);
        return omegaChunk;
    }
}
