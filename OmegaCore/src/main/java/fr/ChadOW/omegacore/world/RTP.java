package fr.ChadOW.omegacore.world;

import fr.ChadOW.omegacore.P;
import fr.ChadOW.omegacore.utils.NbrReader;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class RTP {

    private static final ArrayList<Material> toIgnore = new ArrayList(Arrays.asList(
            Material.AIR,
            Material.ACACIA_LEAVES,
            Material.BIRCH_LEAVES,
            Material.DARK_OAK_LEAVES,
            Material.JUNGLE_LEAVES,
            Material.OAK_LEAVES,
            Material.SPRUCE_LEAVES
    ));

    public static final HashMap<Player, Long> timer10 = new HashMap<>();
    public static final HashMap<Player, Long> timer1 = new HashMap<>();

    public static void tryRandomTeleportation(Player player) {
        Location loc = player.getLocation();

        World world = loc.getWorld();
        String worldName = world.getName();
        if (worldName.equals(Spawn.LIBRE.getWorldName()) || worldName.equals(Spawn.LIBRE_NETHER.getWorldName()) || worldName.equals(Spawn.LIBRE_END.getWorldName())) {
            if (!timer10.containsKey(player) || System.currentTimeMillis() - timer10.get(player) >= 600000) {
                player.sendMessage(WorldManager.prefix + "Recherche d'une localisation ...");
                randomTeleportation(player);
                timer10.put(player, System.currentTimeMillis());
                player.sendMessage(WorldManager.prefix + "Téléportation !");
            } else {
                player.sendMessage(WorldManager.prefix + "§cDésolé §fIl vous reste §c" + NbrReader.getTimeInString(600 - (System.currentTimeMillis() - timer10.get(player))/1000) + "§f à attendre avant de pouvoir vous retéléporter.");
            }
        }
        else if (worldName.equals(Spawn.RESSOURCE.getWorldName()) || worldName.equals(Spawn.RESSOURCE_NETHER.getWorldName()) || worldName.equals(Spawn.RESSOURCE_NETHER.getWorldName())) {
            if (!timer1.containsKey(player) || System.currentTimeMillis() - timer1.get(player) >= 60000) {
                player.sendMessage(WorldManager.prefix + "Recherche d'une localisation ...");
                randomTeleportation(player);
                timer1.put(player, System.currentTimeMillis());
                player.sendMessage(WorldManager.prefix + "Téléportation !");
            } else {
                player.sendMessage(WorldManager.prefix + "§cDésolé §fIl vous reste §c" + NbrReader.getTimeInString(60 - (System.currentTimeMillis() - timer1.get(player))/1000) + "§f à attendre avant de pouvoir vous retéléporter.");
            }
        } else {
            player.sendMessage(WorldManager.prefix + "Ce monde n'accepte pas la téléportation aléatoire.");
        }
    }

    private static void randomTeleportation(Player player) {
        Location loc = player.getLocation();
        World world = loc.getWorld();

        int x, y;
        Location newLoc;
        Material type;

        do {
            //Trouve une coordonné à 1000 blocks du 0 0
            do {
                x = P.random.nextInt(20000) - 10000;
                y = P.random.nextInt(20000) - 10000;
            } while (NbrReader.calcPyth(x, y, 0, 0) < 1000);

            //On va chercher le block le plus haut sur lequel on peut se poser
            newLoc = new Location(world, x, 257, y);
            do {
                newLoc.add(0, -1, 0);
                type = world.getBlockAt(newLoc).getType();
            } while (newLoc.getY() > 50 && toIgnore.contains(type));

            type = world.getBlockAt(newLoc).getType();
        } while (newLoc.getY() == 50 || type.equals(Material.WATER) || type.equals(Material.LAVA));

        player.teleport(newLoc.add(0.5, 1, 0.5));
    }
}
