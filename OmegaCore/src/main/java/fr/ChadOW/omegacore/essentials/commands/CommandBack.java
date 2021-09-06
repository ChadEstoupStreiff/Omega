package fr.ChadOW.omegacore.essentials.commands;

import fr.ChadOW.api.accounts.UserAccount;
import fr.ChadOW.api.enums.Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandBack implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 0 && UserAccount.getAccount(((Player) sender).getUniqueId()).getRank().getPower() >= Rank.LEGEND.getPower()) {
                //todo tp
            }
            else {
                //todo printdoc
            }
        }
        else {
            //todo printdoc
        }
        return true;
    }
}
