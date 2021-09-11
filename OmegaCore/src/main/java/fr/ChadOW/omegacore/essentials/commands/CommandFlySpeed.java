package fr.ChadOW.omegacore.essentials.commands;

import fr.ChadOW.api.accounts.UserAccount;
import fr.ChadOW.api.enums.Rank;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandFlySpeed implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player){
            Player player = ((Player) sender);
            if (UserAccount.getAccount(player.getUniqueId()).getRank().getPower() >= Rank.ADMIN.getPower()){
                if (args.length == 1) {
                    if (NumberUtils.isNumber(args[0])) {
                        player.setFlySpeed(Float.parseFloat(args[0]));

                    }
                }
                else if (args.length == 2){
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null){
                        if (NumberUtils.isNumber(args[1])) {
                            target.setFlySpeed(Float.parseFloat(args[1]));
                        }
                    }
                    else {
                        player.sendMessage("Le joueur n'existe pas ou est hors-ligne.");
                    }
                }
            }
            else {
                sender.sendMessage("Vous n'avez pas la permission suffisante pour exécuter cette commande.");
            }
        }
        else {
            sender.sendMessage("Vous devez être un joueur pour exécuter cette commande.");
        }
        return true;
    }
}
