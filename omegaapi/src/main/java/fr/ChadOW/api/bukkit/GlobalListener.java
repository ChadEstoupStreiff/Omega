package fr.ChadOW.api.bukkit;

import fr.ChadOW.api.bukkit.BukkitAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class GlobalListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        BukkitAPI.resetDisplay(event.getPlayer());
    }
}
