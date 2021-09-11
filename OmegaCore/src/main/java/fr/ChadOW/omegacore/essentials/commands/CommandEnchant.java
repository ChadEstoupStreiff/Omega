package fr.ChadOW.omegacore.essentials.commands;

import fr.ChadOW.api.accounts.UserAccount;
import fr.ChadOW.api.enums.Rank;
import fr.ChadOW.cinventory.interfaces.ItemCreator;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

public class CommandEnchant implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (UserAccount.getAccount(player.getUniqueId()).getRank().getPower() <= Rank.ADMIN.getPower()){
                sender.sendMessage("Vous n'avez pas la permission suffisante pour exécuter cette commande.");
                return true;
            }

            final ItemStack stack = player.getInventory().getItemInMainHand();
            if (stack.getType() != Material.AIR) {
                int level = 1;
                if (args.length > 1) {
                    try {
                        level = Integer.parseInt(args[1]);
                    } catch (final NumberFormatException exception) {
                        player.sendMessage("L'enchantement n'est pas un nombre");
                        return true;
                    }
                }

                final ItemCreator item = new ItemCreator(stack);
                final Enchantment enchantment = Enchantment.getByName(args[0]);

                if(enchantment == null){
                    player.sendMessage("L'enchantement spécifié est invalide.");
                    return true;
                }

                item.addEnchantment(enchantment, level);
                player.getInventory().setItemInMainHand(item.getItem());
                player.updateInventory();
                final String enchantName = enchantment.getName().toLowerCase(Locale.ENGLISH).replace('_', ' ');
                player.sendMessage("Enchantement §b" + enchantName + " appliqué!");
            }
            else {
                player.sendMessage("Vous n'avez aucun item dans la main.");
            }
        }
        else {
            sender.sendMessage("Vous devez être un joueur pour exécuter cette commande.");
        }
        return true;
    }
}
