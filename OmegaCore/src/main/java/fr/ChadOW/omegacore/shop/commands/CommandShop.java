package fr.ChadOW.omegacore.shop.commands;

import fr.ChadOW.omegacore.shop.Shop;
import fr.ChadOW.omegacore.shop.ShopManager;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandShop implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args[0].equals("create")) {
                Shop shop = new Shop(player.getLocation(), new ItemStack(Material.STONE), 0, 0,
                        0, player.getUniqueId());
                shop.updateShop();
                ShopManager.shops.put(shop.getHorse(),shop);
            }
        }
        return true;
    }
}
