package fr.ChadOW.omegacore.utils.omegaplayer;

import fr.ChadOW.api.accounts.BankAccount;
import fr.ChadOW.api.accounts.JobAccount;
import fr.ChadOW.api.accounts.UserAccount;
import fr.ChadOW.api.accounts.group.Group;
import fr.ChadOW.omegacore.P;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Objects;

public class OmegaPlayer implements Listener {
    private Player player;
    private Location lastDeath;
    private Location lastTp;
    private String nickName;

    OmegaPlayer(Player player) {
        this.player = player;
        P.getInstance().getServer().getPluginManager().registerEvents(this, P.getInstance());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (event.getEntity().equals(getPlayer())) {
            Location location = event.getEntity().getLocation();
            setLastDeath(new Location(location.getWorld(), location.getX(), location.getY(), location.getZ()));
        }
    }

    @EventHandler
    public void onTp (PlayerTeleportEvent event){
        if (Objects.equals(event.getTo(), lastDeath)) return;

        if (event.getPlayer().equals(getPlayer())){
            Location location = event.getFrom();
            setLastTp(new Location(location.getWorld(), location.getX(), location.getY(), location.getZ()));
        }
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }

    private void setLastDeath(Location lastDeath) {
        this.lastDeath = lastDeath;
    }

    private void setLastTp(Location lastTp) {
        this.lastTp = lastTp;
    }

    public Location getLastTp() {
        return lastTp;
    }

    public Player getPlayer() {
        return player;
    }

    public Location getLastDeathLocation() {
        return lastDeath;
    }

    public UserAccount getUserAccount() {
        return UserAccount.getAccount(player.getUniqueId());
    }

    public BankAccount getBankAccount() {
        return UserAccount.getAccount(player.getUniqueId()).getBankAccount();
    }

    public JobAccount getJobAccount() {
        return UserAccount.getAccount(player.getUniqueId()).getJobAccount();
    }

    public Group getGroup() {
        return UserAccount.getAccount(player.getUniqueId()).getGroup();
    }
}
