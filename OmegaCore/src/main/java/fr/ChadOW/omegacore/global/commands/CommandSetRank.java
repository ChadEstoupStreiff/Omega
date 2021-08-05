package fr.ChadOW.omegacore.global.commands;

import fr.ChadOW.api.accounts.UserAccount;
import fr.ChadOW.api.enums.Rank;
import fr.ChadOW.omegacore.P;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSetRank implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player) || UserAccount.getAccount(((Player) sender).getUniqueId()).getRank().hasStaffPower(((Player) sender).getUniqueId(), 10)) {
            if (args.length > 0) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    if (args.length > 1) {
                        if (Rank.isExisting(args[1])) {
                            Rank rank = Rank.valueOf(args[1].toUpperCase());
                            UserAccount account = UserAccount.getAccount(target.getUniqueId());

                            account.setRank(rank);
                            target.setPlayerListName(account.getRank().getTab() + target.getDisplayName());

                            sender.sendMessage(P.getInstance().getPrefix() + "§fLe grade de §b" + target.getName() + "§f est définit sur " + rank.getTab());
                        } else {
                            sender.sendMessage(P.getInstance().getPrefix() + "§fLe grade n'hexite pas.");
                        }
                    } else {
                        sender.sendMessage(P.getInstance().getPrefix() + "§fPrécisez un grade.");
                    }
                } else {
                    sender.sendMessage(P.getInstance().getPrefix() + "§fLe joueur n'est pas connecté.");
                }
            } else {
                sender.sendMessage(P.getInstance().getPrefix() + "§fPrécisez un joueur.");
            }
        } else {
            sender.sendMessage(P.getInstance().getPrefix() + "Vous n'avez pas la permission.");
        }
        return true;
    }
}
