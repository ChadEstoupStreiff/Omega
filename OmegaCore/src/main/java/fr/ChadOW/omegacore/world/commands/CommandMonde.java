package fr.ChadOW.omegacore.world.commands;

import fr.ChadOW.omegacore.P;
import fr.ChadOW.omegacore.world.Spawn;
import fr.ChadOW.omegacore.world.WorldManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandMonde implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                //TODO Reflechir pour les commandes
                /*switch(args[0]) {
                    case "libre":
                        player.sendMessage(WorldManager.prefix + "Téléportation vers le §amonde libre§f.");
                        player.teleport(Spawn.LIBRE.getLocation());
                        break;
                    case "nether":
                        player.sendMessage(WorldManager.prefix + "Téléportation vers le §amonde nether§f.");
                        player.teleport(Spawn.LIBRE_NETHER.getLocation());
                        break;
                    case "end":
                        player.sendMessage(WorldManager.prefix + "Téléportation vers le §amonde end§f.");
                        player.teleport(Spawn.LIBRE_END.getLocation());
                        break;
                    case "r":
                        player.sendMessage(WorldManager.prefix + "Téléportation vers le §amonde ressource§f.");
                        player.teleport(Spawn.RESSOURCE.getLocation());
                        break;
                    case "rnether":
                        player.sendMessage(WorldManager.prefix + "Téléportation vers le §amonde ressource nether§f.");
                        player.teleport(Spawn.RESSOURCE_NETHER.getLocation());
                        break;
                    case "rend":
                        player.sendMessage(WorldManager.prefix + "Téléportation vers le §amonde ressource end§f.");
                        player.teleport(Spawn.RESSOURCE_END.getLocation());
                        break;
                    default:
                        WorldManager.getWorldGUI().open(player);
                        break;
                }*/
            } else
                P.getInstance().getWorldManager().getWorldGUI().open(player);
        }
        return true;
    }
}
