package fr.ChadOW.omegacore.essentials.commands;

import fr.ChadOW.api.accounts.UserAccount;
import fr.ChadOW.api.enums.Rank;
import fr.ChadOW.cinventory.interfaces.ItemCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CommandRepair implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player){
            Player player = (Player)sender;
            if (UserAccount.getAccount(player.getUniqueId()).getRank().getPower() >= Rank.LEGEND.getPower()){
                if (args.length == 1) {
                    if (args[0].equals("hand")) {
                        final ItemCreator itemCreator = new ItemCreator(player.getInventory().getItemInMainHand());
                        itemCreator.setDurability(0);
                        player.getInventory().setItemInMainHand(itemCreator.getItem());
                        player.updateInventory();
                    }
                    if (args[0].equals("all")){
                        ItemStack[] contents = player.getInventory().getContents();
                        for (int i = 0; i < contents.length; i++){
                            final ItemCreator itemCreator = new ItemCreator(contents[i]);
                            itemCreator.setDurability(0);
                            contents[i] = itemCreator.getItem();
                        }
                        player.getInventory().setContents(contents);
                        player.updateInventory();
                    }
                }
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
