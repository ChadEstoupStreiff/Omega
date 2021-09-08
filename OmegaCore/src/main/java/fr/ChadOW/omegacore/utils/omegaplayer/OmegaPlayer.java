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

public class OmegaPlayer implements Listener {
    private Player player;
    private Location lastDeath;

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

    private void setLastDeath(Location lastDeath) {
        this.lastDeath = lastDeath;
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
