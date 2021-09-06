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
                // TODO: 06/09/2021 method setpowertoolcommand à ajouter
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
