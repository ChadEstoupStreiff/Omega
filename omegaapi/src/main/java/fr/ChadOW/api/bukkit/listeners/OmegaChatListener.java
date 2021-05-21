package fr.ChadOW.api.bukkit.listeners;

import fr.ChadOW.api.accounts.UserAccount;
import fr.ChadOW.api.bukkit.chat.OmegaChatEvent;
import fr.ChadOW.api.enums.Rank;
import fr.ChadOW.api.bukkit.BukkitAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class OmegaChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);

        OmegaChatEvent omegaChatEvent = new OmegaChatEvent(event, event.getMessage());
        //ListenerManager.callEvent(omegaChatEvent);

        if (!omegaChatEvent.isCanceled()) {
            Player player = event.getPlayer();
            Rank Rank = UserAccount.getAccount(player.getUniqueId()).getRank();
            Bukkit.broadcastMessage(Rank.getPrefix() + player.getDisplayName() + Rank.getSuffix() + omegaChatEvent.message);
        }
    }
}
