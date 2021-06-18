package fr.ChadOW.omegacore.global.bukkit;

import fr.ChadOW.api.accounts.BankAccount;
import fr.ChadOW.api.accounts.JobAccount;
import fr.ChadOW.api.accounts.UserAccount;
import fr.ChadOW.omegacore.global.Global;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAccount implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;

            final UserAccount userAccount = UserAccount.getAccount(player.getUniqueId());
            final BankAccount bankAccount = userAccount.getBankAccount();
            final JobAccount jobAccount = userAccount.getJobAccount();

            player.sendMessage(Global.prefix + "Vos informations de compte:");
            player.sendMessage("§eUUID Compte: §f" + userAccount.getUUID());
            player.sendMessage("§eCrédits: §f" + userAccount.getCredits());
            player.sendMessage("§eID Bancaire: §f" + userAccount.getBankID());
            player.sendMessage("§e   Nom: §f" + bankAccount.getName());
            player.sendMessage("§e   Fonds: §f" + bankAccount.getAmount());
            player.sendMessage("§eID Métier: §f" + userAccount.getJobID());
            player.sendMessage("§e   Type: §f" + jobAccount.getJob());
            player.sendMessage("§e   Niveau: §f" + jobAccount.getLevel());
            player.sendMessage("§e   Expérience: §f" + jobAccount.getExp());
        }
        return true;
    }
}
