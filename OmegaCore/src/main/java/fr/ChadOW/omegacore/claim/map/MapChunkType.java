package fr.ChadOW.omegacore.claim.map;

import fr.ChadOW.omegacore.claim.OmegaChunk;
import net.md_5.bungee.api.ChatColor;

public enum MapChunkType {

    YOU("Vous", ChatColor.GREEN),
    FREE("Libre", ChatColor.GRAY),
    TAKEN("Prise", ChatColor.RED),
    AT("Tu es la", ChatColor.YELLOW);

    private String name;
    private ChatColor color;

    MapChunkType(String name, ChatColor color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public ChatColor getColor() {
        return color;
    }

    public static MapChunkType getTypeByChunk(final OmegaChunk chunk, String owner){
        if (chunk.getOwnerID() == null) return FREE;
        if (chunk.getOwnerID().equalsIgnoreCase(owner)) return YOU;
        return TAKEN;
    }
}
