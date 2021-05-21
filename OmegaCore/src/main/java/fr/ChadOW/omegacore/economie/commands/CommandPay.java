package fr.ChadOW.omegacore.economie.commands;

import fr.ChadOW.api.accounts.BankAccount;
import fr.ChadOW.api.accounts.UserAccount;
import fr.ChadOW.omegacore.economie.Eco;
import fr.ChadOW.omegacore.utils.NbrReader;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandPay implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                if (args.length > 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        if (!player.equals(target)) {
                            if (NbrReader.isDouble(args[1])) {
                                double amount = Double.parseDouble(args[1]);
                                if (amount > 0) {
                                    BankAccount playerAccount = UserAccount.getAccount(player.getUniqueId()).getBankAccount();
                                    if (playerAccount.getAmount() >= amount) {
                                        BankAccount targetAccount = UserAccount.getAccount(target.getUniqueId()).getBankAccount();

                                        playerAccount.payAccount(amount, targetAccount);

                                        player.sendMessage(Eco.prefix + "Vous avez envoyé §a" + amount + Eco.devise + "§f à §b" + target.getName() + "§f.");
                                        target.sendMessage(Eco.prefix + "Vous avez reçu §a" + amount + Eco.devise + "§f de §b" + player.getName() + "§f.");
                                    } else {
                                        player.sendMessage(Eco.prefix + "Il vous manque §c" + (amount - playerAccount.getAmount()) + "§f pour effectuer la transaction.");
                                    }
                                } else {
                                    player.sendMessage(Eco.prefix + "Le montant doit être supérieur à 0.");
                                }
                            } else {
                                player.sendMessage(Eco.prefix + "Le montant est incorrect.");
                            }
                        } else {
                            player.sendMessage(Eco.prefix + "Vous ne pouvez pas vous faire un virement.");
                        }
                    } else {
                        player.sendMessage(Eco.prefix + "Joueur introuvable.");
                    }
                } else {
                    player.sendMessage(Eco.prefix + "Préciser un montant.");
                }
            } else {
                player.sendMessage(Eco.prefix + "Préciser un joueur.");
            }
        }
        return true;
    }
}
