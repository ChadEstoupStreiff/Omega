package fr.ChadOW.omegacore.global.bukkit;

import fr.ChadOW.omegacore.global.Global;
import fr.ChadOW.omegacore.utils.pluginmessage.PluginMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.HashMap;

public class GlobalListener implements Listener {

    private HashMap<Player, Long> cmdCooldown = new HashMap<>();


    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        if (cmdCooldown.containsKey(player) && System.currentTimeMillis() - cmdCooldown.get(player) < 500) {
            event.setCancelled(true);
            player.sendMessage(Global.prefix + "Veuillez attendre §c0.5 secondes §fentre chaque commande.");
        } else {
            cmdCooldown.put(player, System.currentTimeMillis());
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
        PluginMessage.sendPlayerChatMessage(event.getPlayer(), event.getMessage());
    }
}
