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
                final Hologram originalHologram = new Hologram(lineName)
                        .setSmall(false)
                        .setArms(false)
                        .setBaseplate(false)
                        .setVisible(false)
                        .setMarker(false);
                originalHologram.spawn(player.getLocation());
                originalHologram.addSimpleLine("line 2", Hologram.LineDirection.UP)
                        .addSimpleLine("line 3", Hologram.LineDirection.DOWN)
                        .addSimpleLine("line 4", Hologram.LineDirection.UP)
                        .addSimpleLine("line 5", Hologram.LineDirection.UP);
            }
        }
        return true;
    }
}
