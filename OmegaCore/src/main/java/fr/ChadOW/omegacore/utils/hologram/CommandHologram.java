package fr.ChadOW.omegacore.utils.hologram;

import fr.ChadOW.api.accounts.UserAccount;
import fr.ChadOW.omegacore.economie.Eco;
import fr.ChadOW.omegacore.utils.hologram.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class CommandHologram  implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0){
                final String lineName = args[0];
                final Hologram originalHologram = new Hologram(lineName);
                originalHologram.spawn(player.getLocation());
                /*originalHologram.addNormalLines("line 2", Hologram.LineDirection.UP)
                        .addNormalLines("line 3", Hologram.LineDirection.DOWN)
                        .addNormalLines("line 4", Hologram.LineDirection.UP)
                        .addNormalLines("line 5", Hologram.LineDirection.DOWN);*/
                player.sendMessage("§aResults: ");
                player.sendMessage("§bArmorStand List: " + originalHologram.getArmorStands().size());
                player.sendMessage("§bHas Spawned: " + (originalHologram.getMainArmorStand() != null));
            }
        }
        return true;
    }
}
