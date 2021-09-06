package fr.ChadOW.omegacore.essentials.commands;

import fr.ChadOW.api.accounts.UserAccount;
import fr.ChadOW.api.enums.Rank;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandGod implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player){
            Player player = ((Player) sender);
            if (UserAccount.getAccount(player.getUniqueId()).getRank().getPower() >= Rank.ADMIN.getPower()){
                if (args.length == 0) {
                    player.setInvisible(!player.isInvisible());
                }
                else if (args.length == 1){
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null){
                        target.setInvisible(!target.isInvisible());
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
