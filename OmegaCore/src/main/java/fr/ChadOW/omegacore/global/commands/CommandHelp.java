package fr.ChadOW.omegacore.global.commands;

import fr.ChadOW.cinventory.CContent.CInventory;
import fr.ChadOW.cinventory.CContent.CItem;
import fr.ChadOW.cinventory.interfaces.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class CommandHelp implements CommandExecutor {

    private final CInventory inv;

    public CommandHelp() {
        inv = new CInventory(54, "§8§lMenu des commandes");

        int[] white = new int[]{3,4,5,11,12,13,14,15,19,20,22,24,25,28,29,31,33,34,38,39,40,41,42,48,49,50};
        int[] orange = new int[]{0,1,2,6,7,8,9,10,16,17,18,21,23,26,27,30,32,35,36,37,43,44,45,46,47,51,52,53};

        for (int i : white)
            inv.addElement(new CItem(new ItemCreator(Material.WHITE_STAINED_GLASS_PANE, 0).setName("§f")).setSlot(i));
        for (int i : orange)
            inv.addElement(new CItem(new ItemCreator(Material.ORANGE_STAINED_GLASS_PANE, 0).setName("§f")).setSlot(i));

        inv.addElement(new CItem(new ItemCreator(Material.DIAMOND_PICKAXE, 0).setName("§6Métiers").setLores(Arrays.asList(
                "§f",
                "§fPour voir le menu des métiers, faites la commande:",
                "§b   /job",
                "§f",
                "§7Clique moi pour en savoir plus"
        ))).addEvent((inventory, item, player, clickContext) -> Bukkit.dispatchCommand(player, "job")).setSlot(22));

        inv.addElement(new CItem(new ItemCreator(Material.GRASS_BLOCK, 0).setName("§6Mondes").setLores(Arrays.asList(
                "§f",
                "§fPour voir les mondes, faites la commande:",
                "§b   /monde",
                "§f",
                "§7Clique moi pour en savoir plus"
        ))).addEvent((inventory, item, player, clickContext) -> Bukkit.dispatchCommand(player, "monde")).setSlot(20));

        inv.addElement(new CItem(new ItemCreator(Material.GOLD_INGOT, 0).setName("§6Grades").setLores(Arrays.asList(
                "§f",
                "§fPour voir les grades, faites la commande:",
                "§b   /grade",
                "§f",
                "§7Clique moi pour en savoir plus"
        ))).addEvent((inventory, item, player, clickContext) -> Bukkit.dispatchCommand(player, "grade")).setSlot(24));
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player)
            inv.open((Player) commandSender);
        return true;
    }
}
