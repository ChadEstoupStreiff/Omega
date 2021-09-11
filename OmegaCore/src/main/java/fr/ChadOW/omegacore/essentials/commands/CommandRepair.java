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
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

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
                        ItemStack itemStack = player.getInventory().getItemInMainHand();
                        if (itemStack instanceof Damageable) {
                            itemStack.setDurability((short) 0);
                        }
                        player.getInventory().setItemInMainHand(itemStack);
                        player.updateInventory();
                    }
                    if (args[0].equals("all")){
                        ItemStack[] contents = player.getInventory().getContents();
                        for (int i = 0; i < contents.length; i++){
                            ItemStack itemStack = contents[i];
                            if (itemStack instanceof Damageable) {
                                itemStack.setDurability((short) 0);
                                contents[i] = itemStack;
                            }
                        }
                        player.getInventory().setContents(contents);
                        player.updateInventory();
                    }
                }
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
