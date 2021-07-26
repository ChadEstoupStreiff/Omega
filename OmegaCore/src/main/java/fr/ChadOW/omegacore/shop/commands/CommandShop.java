package fr.ChadOW.omegacore.shop.commands;

import fr.ChadOW.omegacore.P;
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
                P.getInstance().getShopManager().createShop(player.getLocation().getBlock().getLocation().add(0.5, 0.1, 0.5), new ItemStack(Material.STONE), 0, 0,
                        0, player.getUniqueId());
            }
        }
        return true;
    }
}
