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
                else if (args.length == 1){
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null){
                        target.setFoodLevel(20);
                    }
                    else {
                        //todo print doc
                    }
                }
            }
            else {
                // TODO: 06/09/2021 print doc
            }
        }
        else {
            // TODO: 06/09/2021 print doc
        }
        return true;
    }
}
