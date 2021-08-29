package fr.ChadOW.omegacore.utils.hologram;

import fr.ChadOW.api.accounts.UserAccount;
import fr.ChadOW.api.enums.Rank;
import fr.ChadOW.omegacore.P;
import fr.ChadOW.omegacore.utils.NbrReader;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandHologram implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (UserAccount.getAccount(player.getUniqueId()).getRank().getStaffPower() >= Rank.DEV.getStaffPower()) {
                HologramManager hm = P.getInstance().getHologramManager();
                if (args.length > 0) {
                    if (args[0].equalsIgnoreCase("list")){
                        player.sendMessage("Liste des hologrammes");
                        player.sendMessage("Cliquez sur l'hologramme pour vous y téléporter");
                        List<Hologram> hologramList = hm.getHolograms();
                        for (Hologram hologram : hologramList){
                            TextComponent textComponent = new TextComponent(String.format(ChatColor.YELLOW + "Hologram n°%s, nom: %s",hologramList.indexOf(hologram),hologram.getName()));
                            textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,String.format("/hologram teleport %s",hologramList.indexOf(hologram))));
                            player.spigot().sendMessage(textComponent);
                        }
                    }
                    else if (args[0].equalsIgnoreCase("create")) {
                        String name = args[1];
                        for (String s : args)
                            if (!s.equals(args[0]) && !s.equals(args[1]))
                                name = name.concat(" "+s);
                        hm.createHologram(name, player.getLocation());
                        player.sendMessage(ChatColor.GREEN + "Hologram " + name + " créé à votre position!");
                    }
                    else if (args[0].equalsIgnoreCase("teleport")){
                        if (NbrReader.isNumber(args[1])) {
                            int id = Integer.parseInt(args[1]);
                            if (hm.getHolograms().size() > id) {
                                player.teleport(hm.getHolograms().get(id).getLocation());
                            } else player.sendMessage(ChatColor.RED + "Cet hologramme n'existe pas!");
                        } else player.sendMessage(ChatColor.RED + "Vous devez indiquer le n° de l'Hologramme (faites / "+ cmd.getName()+" list pour le voir)");
                    }
                    else if (args[0].equalsIgnoreCase("radiuslist")){
                        if (NbrReader.isNumber(args[1])) {
                            int radius = Integer.parseInt(args[1]);
                            player.sendMessage("Liste des hologrammes à max " + radius + " blocks");
                            player.sendMessage("Cliquez sur l'hologramme pour vous y téléporter");
                            List<Hologram> hologramList = hm.getHolograms();
                            for (Hologram hologram : hologramList){
                                if (hologram.getLocation().distance(player.getLocation()) < radius) {
                                    TextComponent textComponent = new TextComponent(String.format(ChatColor.YELLOW + "Hologram n°%s, nom: %s", hologramList.indexOf(hologram), hologram.getName()));
                                    textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/hologram teleport %s", hologramList.indexOf(hologram))));
                                    player.spigot().sendMessage(textComponent);
                                }
                            }
                        } else player.sendMessage(ChatColor.RED + "Vous devez indiquer le rayon de recherche");
                    }
                    else if (args[0].equalsIgnoreCase("removeline")){
                        if (NbrReader.isNumber(args[1]) && NbrReader.isNumber(args[2])){
                            int id = Integer.parseInt(args[1]);
                            if (hm.getHolograms().size() > id) {
                                hm.getHolograms().get(id).removeLine(Integer.parseInt(args[2]));
                                player.sendMessage(ChatColor.GREEN + "Ligne retirée!");
                            }
                            else player.sendMessage(ChatColor.RED + "Cet hologramme n'existe pas!");
                        } else player.sendMessage(ChatColor.RED + "Vous devez indiquer le n° de l'Hologramme (faites / "+ cmd.getName()+" list pour le voir) ainsi que le numéro de ligne");
                    }
                    else if (args[0].equalsIgnoreCase("addlines")){
                        if (NbrReader.isNumber(args[1])){
                            int id = Integer.parseInt(args[1]);
                            if (hm.getHolograms().size() > id) {
                                List<String> lines = new ArrayList<>();
                                for (String s : args)
                                    if (!s.equals(args[0]) && !s.equals(args[1]))
                                        lines.add(s);
                                hm.getHolograms().get(id).addLines(lines);
                                player.sendMessage(ChatColor.GREEN + "Lignes ajoutées!");
                            } else player.sendMessage(ChatColor.RED + "Cet hologramme n'existe pas!");
                        } else player.sendMessage(ChatColor.RED + "Vous devez indiquer le n° de l'Hologramme (faites / "+ cmd.getName()+" list pour le voir)");
                    }
                    else if (args[0].equalsIgnoreCase("insertline")){
                        if (NbrReader.isNumber(args[1]) && NbrReader.isNumber(args[2])){
                            int id = Integer.parseInt(args[1]);
                            if (hm.getHolograms().size() > id) {
                                Hologram hologram = hm.getHolograms().get(id);
                                hologram.insertLine(args[3], Integer.parseInt(args[2]));
                                player.sendMessage(ChatColor.GREEN + "Ligne insérée!");
                            } else player.sendMessage(ChatColor.RED + "Cet hologramme n'existe pas!");
                        } else player.sendMessage(ChatColor.RED + "Vous devez indiquer le n° de l'Hologramme (faites / "+ cmd.getName()+" list pour le voir) ainsi que le numéro de ligne");
                    }
                    else if (args[0].equalsIgnoreCase("remove")){
                        if (NbrReader.isNumber(args[1])) {
                            int id = Integer.parseInt(args[1]);
                            Hologram hologram = hm.getHolograms().get(id);
                            if (hm.getHolograms().size() > id) {
                                hm.deleteHologram(hologram);
                                player.sendMessage(ChatColor.GREEN + "L'Hologramme " + hologram.getName() + " a été supprimé");
                            } else player.sendMessage(ChatColor.RED + "Cet hologramme n'existe pas!");
                        } else player.sendMessage(ChatColor.RED + "Vous devez indiquer le n° de l'Hologramme (faites / "+ cmd.getName()+" list pour le voir)");
                    } else {
                        printDoc(player);
                    }
                } else {
                    printDoc(player);
                }
            }
        }
        return true;
    }

    private void printDoc(Player player) {
        player.sendMessage("§fCommandes hologram:");
        player.sendMessage("§fPour créer un hologram faites: §e/hologram create <nom>");
    }
}
