package fr.ChadOW.omegacore.essentials.commands;

import fr.ChadOW.api.accounts.UserAccount;
import fr.ChadOW.api.enums.Rank;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class CommandKill implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player){
            Player player = ((Player) sender);
            if (UserAccount.getAccount(player.getUniqueId()).getRank().getPower() >= Rank.ADMIN.getPower()){
                if (args.length == 0) {
                    final EntityDamageEvent ede = new EntityDamageEvent(player, EntityDamageEvent.DamageCause.SUICIDE, Float.MAX_VALUE);
                    Bukkit.getPluginManager().callEvent(ede);
                    if (ede.isCancelled()) {
                        return true;
                    }
                    ede.getEntity().setLastDamageCause(ede);
                    player.setHealth(0);
                }
                else if (args.length == 1){
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null){
                        final EntityDamageEvent ede = new EntityDamageEvent(target, EntityDamageEvent.DamageCause.CUSTOM, Float.MAX_VALUE);
                        Bukkit.getPluginManager().callEvent(ede);
                        if (ede.isCancelled()) {
                            return true;
                        }
                        ede.getEntity().setLastDamageCause(ede);
                        target.setHealth(0);
                        player.sendMessage(target.getName()+" a été tué"); // TODO: 06/09/2021 couleurs
                    }
                    else {
                        //todo print doc
                    }
                }
            }
            else {
                //todo print doc
            }
        }
        else {
            //todo print doc
        }
        return true;
    }
}
