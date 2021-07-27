package fr.ChadOW.omegacore.claim;

import fr.ChadOW.omegacore.P;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ClaimListener implements Listener {

    @EventHandler
    public void onDisconnect(PlayerQuitEvent event) {
        P.getInstance().getClaimManager().setShowOff(event.getPlayer());
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Location location = event.getBlock().getLocation();

        if (P.getInstance().getClaimManager().checkPermission(player, location)) {
            event.setCancelled(true);
            player.sendMessage(P.getInstance().getClaimManager().prefix + "§cCette portion de terre ne vous appartient pas !");
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Location location = event.getBlock().getLocation();

        if (P.getInstance().getClaimManager().checkPermission(player, location)) {
            event.setCancelled(true);
            player.sendMessage(P.getInstance().getClaimManager().prefix + "§cCette portion de terre ne vous appartient pas !");
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null) {
            Player player = event.getPlayer();
            Location location = event.getClickedBlock().getLocation();

            if (P.getInstance().getClaimManager().checkPermission(player, location)) {
                event.setCancelled(true);
                player.sendMessage(P.getInstance().getClaimManager().prefix + "§cCette portion de terre ne vous appartient pas !");
            }
        }
    }
}
