package fr.ChadOW.omegacore.essentials.commands;

import fr.ChadOW.api.accounts.UserAccount;
import fr.ChadOW.api.enums.Rank;
import fr.ChadOW.omegacore.P;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandBack implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            if (UserAccount.getAccount(((Player) sender).getUniqueId()).getRank().getPower() >= Rank.LEGEND.getPower()) {
                if (P.getInstance().getOmegaPlayerManager().getOmegaPlayer((Player) sender).getLastDeathLocation() != null)
                ((Player) sender).teleport(P.getInstance().getOmegaPlayerManager().getOmegaPlayer((Player) sender).getLastDeathLocation());
                sender.sendMessage("Vous avez été téléporté avec succès.");
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
