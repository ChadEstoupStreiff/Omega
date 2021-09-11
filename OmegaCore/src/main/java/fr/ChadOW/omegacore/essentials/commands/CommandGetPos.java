package fr.ChadOW.omegacore.essentials.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandGetPos implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player){
            Player player = ((Player) sender);
            Location loc = player.getLocation();
            player.sendMessage(String.format("Vos coordonnées actuelles sont §b%s§r §b%s§r §b%s§r, yaw: §b%s§r, pitch: §b%s§r",loc.getX(),loc.getY(),loc.getZ(),loc.getYaw(),loc.getPitch()));
        }
        else {
            sender.sendMessage("Vous devez être un joueur pour exécuter cette commande.");
        }
        return true;
    }
}
