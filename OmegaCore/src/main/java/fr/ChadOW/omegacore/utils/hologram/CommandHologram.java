package fr.ChadOW.omegacore.utils.hologram;

import fr.ChadOW.api.accounts.UserAccount;
import fr.ChadOW.api.enums.Rank;
import fr.ChadOW.omegacore.P;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class CommandHologram implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (UserAccount.getAccount(player.getUniqueId()).getRank().getStaffPower() >= Rank.DEV.getStaffPower()) {
                if (args.length > 1) {
                    if (args[0].equalsIgnoreCase("create")) {
                        Hologram hologram = P.getInstance().getHologramManager().createHologram(args[1], player.getLocation());
                        if (args.length > 2)
                            for (String line : args)
                                if (!line.equals(args[0]) && !line.equals(args[1]))
                                    hologram.addLine(line);
                        player.sendMessage(ChatColor.GREEN + "Hologram créé à votre position!");
                    }
                    else if (args[0].equalsIgnoreCase("remove")){
                        if (args.length == 2){
                            String name = args[1];
                            HologramManager hm = P.getInstance().getHologramManager();
                            Hologram hologram = hm.getHologram(name);
                            if (hologram != null) {
                                hm.deleteHologram(hologram);
                                player.sendMessage(ChatColor.GREEN + "L'Hologram " + name + " a été supprimé");
                            }
                            else player.sendMessage(ChatColor.RED + "Cet hologram n'existe pas!");
                        }
                    }
                }
                else player.sendMessage(ChatColor.GOLD + "Pour créer un hologram faites /" + cmd.getName() +" create <nom> <ligne1> <ligne2> ...");
            }
        }
        return true;
    }
}
