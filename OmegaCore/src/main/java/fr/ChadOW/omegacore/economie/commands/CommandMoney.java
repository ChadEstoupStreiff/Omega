package fr.ChadOW.omegacore.economie.commands;

import fr.ChadOW.api.accounts.UserAccount;
import fr.ChadOW.omegacore.economie.Eco;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandMoney implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length < 1) {
                UserAccount account = UserAccount.getAccount(player.getUniqueId());
                player.sendMessage(Eco.prefix + "Vous possédez §a" + account.getBankAccount().getAmount() + Eco.devise + "§f et §b" + account.getCredits() + " crédits§f.");
            } else {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                if (target != null) {
                    UserAccount account = UserAccount.getAccount(target.getUniqueId());
                    sender.sendMessage(Eco.prefix + "§b" + target.getName() + "§f possède " + account.getBankAccount().getAmount() + Eco.devise + "§f et §b" + account.getCredits() + " crédits§f.");
                } else {
                    sender.sendMessage(Eco.prefix + "Joueur introuvable.");
                }
            }
        }
        return true;
    }
}
