package fr.ChadOW.omegacore.world.commands;

import fr.ChadOW.omegacore.world.RTP;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRTP implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player)
            RTP.tryRandomTeleportation((Player) sender);
        return true;
    }
}
