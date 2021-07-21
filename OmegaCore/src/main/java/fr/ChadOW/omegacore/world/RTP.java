package fr.ChadOW.omegacore.world;

import fr.ChadOW.api.accounts.UserAccount;
import fr.ChadOW.api.enums.Rank;
import fr.ChadOW.omegacore.P;
import fr.ChadOW.omegacore.utils.NbrReader;
import fr.ChadOW.omegacore.utils.OmegaUtils;
import fr.ChadOW.omegacore.utils.ServerType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.time.LocalTime;
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
            Material.SPRUCE_LEAVES,
            Material.WATER,
            Material.LAVA
    ));

    private static HashMap<Player, Long> playersCooldown = new HashMap<>();

    public static void tryRandomTeleportation(Player player) {
        int cooldown = -1;
        if (ServerType.equals(ServerType.NORMAL)) {
            UserAccount userAccount = UserAccount.getAccount(player.getUniqueId());
            if (userAccount.getRank().getPower() > 2)
                cooldown = 30;
            else if (userAccount.getRank().getPower() == 2)
                cooldown = 120;
            else if (userAccount.getRank().getPower() == 1)
                cooldown = 300;
            else
                cooldown = 600;
        } else if (ServerType.equals(ServerType.RESSOURCES)) {
            UserAccount userAccount = UserAccount.getAccount(player.getUniqueId());
            if (userAccount.getRank().getPower() > 2)
                cooldown = 5;
            else if (userAccount.getRank().getPower() == 2)
                cooldown = 15;
            else if (userAccount.getRank().getPower() == 1)
                cooldown = 30;
            else
                cooldown = 60;
        }
        if (cooldown >= 0 && (!playersCooldown.containsKey(player) || (System.currentTimeMillis() - playersCooldown.get(player))/1000 >= cooldown))
            randomTeleportation(player);
        else
            player.sendMessage("&6[Monde]&r &cDésolé &ril vous reste &c" + (cooldown - (System.currentTimeMillis() - playersCooldown.get(player))/1000) + " &rsecondes à attendre avant de pouvoir vous retéléporter.");
    }

    private static void randomTeleportation(Player player) {
        player.sendMessage("§6[Monde]§r Calcul d'une localisation ...");
        Location loc = player.getLocation();
        World world = loc.getWorld();

        int x, y;
        Location newLoc;
        Material type;

        do {
            //Trouve une coordonné à 1000 blocks du 0 0
            x = P.random.nextInt(20000) - 10000;
            y = P.random.nextInt(20000) - 10000;

            //On va chercher le block le plus haut sur lequel on peut se poser
            newLoc = new Location(world, x, 257, y);
            do {
                newLoc.add(0, -1, 0);
                type = world.getBlockAt(newLoc).getType();
            } while (newLoc.getY() > 50 && toIgnore.contains(type));

            type = world.getBlockAt(newLoc).getType();
        } while (newLoc.getY() == 50 || toIgnore.contains(type));

        player.teleport(newLoc.add(0.5, 1, 0.5));
        playersCooldown.put(player, System.currentTimeMillis());
        player.sendMessage("§6[Monde]§r Téléportation effectué avec §asuccès§r !");
    }
}
