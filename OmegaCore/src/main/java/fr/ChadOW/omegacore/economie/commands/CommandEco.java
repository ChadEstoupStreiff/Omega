package fr.ChadOW.omegacore.economie.commands;

import fr.ChadOW.api.accounts.BankAccount;
import fr.ChadOW.api.accounts.UserAccount;
import fr.ChadOW.api.enums.Rank;
import fr.ChadOW.omegacore.utils.NbrReader;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandEco implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //Attention || = OU, && = et, ton code ne marchait pas avec les ||
        if ((sender instanceof Player)) {
            Player player = (Player) sender;
            //Pour savoir quel power on met, on utilise les enumerations de la Class Rank
            if (UserAccount.getAccount(player.getUniqueId()).getRank().hasStaffPower(player.getUniqueId(), Rank.DEV.getStaffPower())){
                if (args.length > 0) {
                    final Player user = (Player) sender;
                    final Player target = Bukkit.getPlayer(args[0]);

                    if (target != null) {
                        //Ajout d'un check si le string est un double pour éviter le crash du cast
                        if (args.length > 1 && NbrReader.isDouble(args[1])) {
                            final double amount = Double.parseDouble(args[1]);
                            final UserAccount userAccount = UserAccount.getAccount(target.getUniqueId());
                            final BankAccount bankAccount = userAccount.getBankAccount();

                            if (bankAccount != null) {
                                //Switch pk pas, c'est rentable niveau puissance à partir de 6 surtout, te prend pas la tête la prochaine fois ^^
                                switch (args[0]) {

                                    case "add":
                                        bankAccount.addAmount(amount);

                                        user.sendMessage(ChatColor.GOLD + "[Economie] " + ChatColor.WHITE + "Ajout de " + ChatColor.GREEN + amount + ChatColor.WHITE + " à " + ChatColor.GREEN + target.getName());
                                        break;
                                    case "remove":
                                        bankAccount.removeAmount(amount);

                                        user.sendMessage(ChatColor.GOLD + "[Economie] " + ChatColor.WHITE + "Tirage de " + ChatColor.GREEN + amount + ChatColor.WHITE + " du compte à " + ChatColor.GREEN + target.getName());
                                        break;

                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
