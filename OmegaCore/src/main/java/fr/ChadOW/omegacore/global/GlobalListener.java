package fr.ChadOW.omegacore.global;

import fr.ChadOW.api.bukkit.BukkitAPI;
import fr.ChadOW.omegacore.P;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;

public class GlobalListener implements Listener {

    private final HashMap<Player, Long> cmdCooldown = new HashMap<>();


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        BukkitAPI.resetDisplay(event.getPlayer());
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        if (cmdCooldown.containsKey(player) && System.currentTimeMillis() - cmdCooldown.get(player) < 500) {
            event.setCancelled(true);
            player.sendMessage(P.getInstance().getPrefix() + "Veuillez attendre §c0.5 secondes §fentre chaque commande.");
        } else {
            cmdCooldown.put(player, System.currentTimeMillis());
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
        P.getInstance().getPluginMessage().sendPlayerChatMessage(event.getPlayer(), event.getMessage());
    }
}
