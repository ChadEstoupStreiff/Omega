package fr.ChadOW.omegacore.claim;

import com.google.common.collect.Lists;
import fr.ChadOW.api.bukkit.OmegaChunk.OmegaChunk;
import fr.ChadOW.omegacore.P;
import fr.ChadOW.omegacore.claim.commands.ClaimCommand;
import fr.ChadOW.omegacore.claim.commands.UnClaimCommand;
import fr.ChadOW.omegacore.claim.map.MapChunkType;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Chunk;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Claim {
    public static final String prefix = "§6[§eOmegaChunk§6] §f";
    private static final List<Player> showPlayers = new ArrayList<>();

    public static void init(P i) {
        i.getCommand("claim").setExecutor(new ClaimCommand());
        i.getCommand("unclaim").setExecutor(new UnClaimCommand());
        i.getServer().getPluginManager().registerEvents(new ClaimListener(), i);

        i.getServer().getScheduler().runTaskTimer(i, () -> {
            if (showPlayers.size() > 0) {
                Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(255, 0, 0), 1);
                for (Player player : showPlayers) {
                    OmegaChunk omegaChunk = OmegaChunk.getChunk(player.getLocation());

                    int minX = omegaChunk.getX() * 32;
                    int minZ = omegaChunk.getY() * 32;
                    double y = player.getLocation().getY() + 1;

                    for (int iterator = 0; iterator <= 32; iterator ++) {
                        player.spawnParticle(Particle.REDSTONE, minX + iterator, y, minZ, 1, dustOptions);
                        player.spawnParticle(Particle.REDSTONE, minX, y, minZ + iterator, 1, dustOptions);
                        player.spawnParticle(Particle.REDSTONE, minX + iterator, y, minZ + 32, 1, dustOptions);
                        player.spawnParticle(Particle.REDSTONE, minX + 32, y, minZ + iterator, 1, dustOptions);
                    }
                }
            }
        }, 5, 5);
    }

    public static LinkedList<TextComponent> getMap(final Location location, final int height, final int width, String owner) {
        final LinkedList<TextComponent> ret = Lists.newLinkedList();

        ret.add(new TextComponent(" "));

        int halfWidth = width / 2;
        int halfHeight = height / 2;

        OmegaChunk omegaChunk = OmegaChunk.getChunk(location);
        Chunk topLeft = location.getWorld().getChunkAt(omegaChunk.getX() -halfWidth,omegaChunk.getY() -halfHeight);

        for (int dz = 0; dz < height; dz++) {
            TextComponent line = new TextComponent();

            for (int dx = 0; dx < width; dx++) {
                omegaChunk = OmegaChunk.getChunk(location.getWorld(), topLeft.getX() + dx, topLeft.getZ() + dz);
                MapChunkType type = MapChunkType.getTypeByChunk(omegaChunk, owner);
                if (dx == halfWidth && dz == halfHeight)
                    type = MapChunkType.AT;

                String overText = "(" + omegaChunk.getX() + ";" + omegaChunk.getY() +"): ";
                switch (type){
                    case AT :
                        overText += "Vous êtes ici !";
                        break;
                    case YOU :
                        overText += "Votre claim ";
                        break;
                    case TAKEN :
                        overText += "Occupé par " + omegaChunk.getOwnerID();
                        break;
                    default:
                        overText += "Libre";
                        break;
                }
                overText = type.getColor() + overText;

                TextComponent text = new TextComponent(String.valueOf(type.getColor()) + '▇');
                text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(overText)));
                line.addExtra(text);
            }
            ret.add(line);
        }

        ret.add(new TextComponent(" "));
        return ret;
    }

    /**
     *
     * Check if a Specific has the permission to do
     * an action in a specific Location
     *
     * @param player
     * @param location
     * @return permission
     */
    public static boolean checkPermission(Player player, Location location) {
        OmegaChunk chunk = OmegaChunk.getChunk(location);
        return chunk.getOwnerID().equalsIgnoreCase(player.getUniqueId().toString());
    }

    public static void switchShow(Player player) {
        if (showPlayers.contains(player)) {
            showPlayers.remove(player);
            player.sendMessage(prefix + "Visualisation des parcelles de terre désactivé.");
        }
        else {
            showPlayers.add(player);
            player.sendMessage(prefix + "Visualisation des parcelles de terre activé.");
        }
    }

    public static void setShowOff(Player player) {
        showPlayers.remove(player);
    }

    public static void setShowOn(Player player) {
        if (!showPlayers.contains(player))
            showPlayers.add(player);
    }


}
