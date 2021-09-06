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
            player.sendMessage(String.format("Vos coordonnées actuelles sont %s %s %s, yaw: %s, pitch: %s",loc.getX(),loc.getY(),loc.getZ(),loc.getYaw(),loc.getPitch())); // TODO: 06/09/2021 couleurs
        }
        else {
            //todo print doc
        }
        return true;
    }
}
