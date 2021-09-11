package fr.ChadOW.omegacore.utils.omegaplayer;

import fr.ChadOW.omegacore.P;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

public class OmegaPlayerManager implements Listener {
    private final List<OmegaPlayer> omegaPlayers;

    public OmegaPlayerManager(P p) {
        omegaPlayers = new ArrayList<>();
        p.getServer().getPluginManager().registerEvents(this, p);
    }

    private OmegaPlayer registerPlayer(Player player) {
        OmegaPlayer omegaPlayer = new OmegaPlayer(player);
        omegaPlayers.add(omegaPlayer);
        return omegaPlayer;
    }

    private void unregisterPlayer(Player player) {
        omegaPlayers.remove(getOmegaPlayer(player));
    }

    public OmegaPlayer getOmegaPlayer(Player player) {
        for (OmegaPlayer omegaPlayer : omegaPlayers) {
            if (omegaPlayer.getPlayer().equals(player))
                return omegaPlayer;
        }
        return registerPlayer(player);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        registerPlayer(event.getPlayer());
    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent event) {
        unregisterPlayer(event.getPlayer());
    }
}
