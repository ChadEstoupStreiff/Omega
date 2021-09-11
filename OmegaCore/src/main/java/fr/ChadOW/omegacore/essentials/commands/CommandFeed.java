package fr.ChadOW.omegacore.essentials.commands;

import fr.ChadOW.api.accounts.UserAccount;
import fr.ChadOW.api.enums.Rank;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandFeed implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player){
            Player player = (Player)sender;
            if (UserAccount.getAccount(player.getUniqueId()).getRank().getPower() >= Rank.OLD.getPower()){
                if (args.length == 0){
                    player.setFoodLevel(20);
                }
                else if (args.length == 1 && UserAccount.getAccount(player.getUniqueId()).getRank().getPower() >= Rank.ADMIN.getPower()){
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null){
                        target.setFoodLevel(20);
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
