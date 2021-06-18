package fr.ChadOW.omegacore.utils.hologram;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class CommandHologram implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0){
                //TODO print DOC
                Hologram hologram = new Hologram("test", player.getLocation(), Arrays.asList("§cPremière ligne", "§bSeconde ligne", "§aTo delete"));
            }
        }
        return true;
    }
}
