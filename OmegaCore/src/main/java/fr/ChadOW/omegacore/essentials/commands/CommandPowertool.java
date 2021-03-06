package fr.ChadOW.omegacore.essentials.commands;

import fr.ChadOW.api.accounts.UserAccount;
import fr.ChadOW.api.enums.Rank;
import fr.ChadOW.cinventory.interfaces.ItemCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandPowertool implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player){
            Player player = (Player)sender;
            if (UserAccount.getAccount(player.getUniqueId()).getRank().getPower() >= Rank.ADMIN.getPower()){
                final ItemCreator itemCreator = new ItemCreator(player.getInventory().getItemInMainHand());
                // TODO: 06/09/2021 method setItemAction à ajouter
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
