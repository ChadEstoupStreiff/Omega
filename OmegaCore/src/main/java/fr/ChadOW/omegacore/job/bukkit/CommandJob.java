package fr.ChadOW.omegacore.job.bukkit;

import fr.ChadOW.omegacore.job.JobManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandJob implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            JobManager.openMainGUI((Player) sender);
        }
        return true;
    }
}
