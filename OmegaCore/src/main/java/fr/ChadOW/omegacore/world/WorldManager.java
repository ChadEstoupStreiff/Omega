package fr.ChadOW.omegacore.world;

import fr.ChadOW.cinventory.CContent.CInventory;
import fr.ChadOW.cinventory.CContent.CItem;
import fr.ChadOW.cinventory.ItemCreator;
import fr.ChadOW.omegacore.P;
import fr.ChadOW.omegacore.world.commands.CommandMonde;
import fr.ChadOW.omegacore.world.commands.CommandRTP;
import org.bukkit.*;

import java.util.Arrays;

public class WorldManager {

    private static CInventory worldGUI;

    public static final String prefix = "§6[Worlds] §f";

    public static void init(P i) {
        createWorlds(i);
        Spawn.updateLocation();
        createWorldGUI();

        i.getCommand("monde").setExecutor(new CommandMonde());
        i.getCommand("rtp").setExecutor(new CommandRTP());
    }

    private static void createWorldGUI() {
        worldGUI = new CInventory(27, "§eMenu mondes");

        CItem item = new CItem(
                new ItemCreator(Material.DIRT, (byte) 0)
                        .setName("§6Mondes libres")
                        .setLores(Arrays.asList(
                                "§7",
                                "§fMondes de construction dans l'overworld",
                                "§f(§aclaims disponibles§f)"
                        )))
                .setSlot(10);
        item.addEvent((cInventory, cItem, player, clickContext) -> {
            player.sendMessage(prefix + "Téléportation vers le §amonde libre§f.");
            player.teleport(Spawn.LIBRE.getLocation());
        });
        worldGUI.addElement(item);

        item = new CItem(
                new ItemCreator(Material.NETHERRACK, (byte) 0)
                        .setName("§6Mondes Libre Nether")
                        .setLores(Arrays.asList(
                                "§7",
                                "§fMondes de construction dans le nether",
                                "§f(§aclaims disponibles§f)"
                        )))
                .setSlot(11);
        item.addEvent((cInventory, cItem, player, clickContext) -> {
            player.sendMessage(prefix + "Téléportation vers le §amonde libre nether§f.");
            player.teleport(Spawn.LIBRE_NETHER.getLocation());
        });
        worldGUI.addElement(item);

        item = new CItem(
                new ItemCreator(Material.END_STONE, (byte) 0)
                        .setName("§6Mondes Libre End")
                        .setLores(Arrays.asList(
                                "§7",
                                "§fMondes de construction dans l'end",
                                "§f(§aclaims disponibles§f)"
                        )))
                .setSlot(12);
        item.addEvent((cInventory, cItem, player, clickContext) -> {
            player.sendMessage(prefix + "Téléportation vers le §amonde libre end§f.");
            player.teleport(Spawn.LIBRE_END.getLocation());
        });
        worldGUI.addElement(item);

        item = new CItem(
                new ItemCreator(Material.DIAMOND_PICKAXE, (byte) 0)
                        .setName("§6Mondes Ressource")
                        .setLores(Arrays.asList(
                                "§7",
                                "§fMondes de récolte de ressources dans l'overworld",
                                "§f(§aclaims disponibles§f)"
                        )))
                .setSlot(14);
        item.addEvent((cInventory, cItem, player, clickContext) -> {
            player.sendMessage(prefix + "Téléportation vers le §amonde ressource§f.");
            player.teleport(Spawn.RESSOURCE.getLocation());
        });
        worldGUI.addElement(item);

        item = new CItem(
                new ItemCreator(Material.WARPED_HYPHAE, (byte) 0)
                        .setName("§6Mondes Ressource Nether")
                        .setLores(Arrays.asList(
                                "§7",
                                "§fMondes de récolte de ressources dans le nether",
                                "§f(§aclaims disponibles§f)"
                        )))
                .setSlot(15);
        item.addEvent((cInventory, cItem, player, clickContext) -> {
            player.sendMessage(prefix + "Téléportation vers le §amonde ressource nether§f.");
            player.teleport(Spawn.RESSOURCE_NETHER.getLocation());
        });
        worldGUI.addElement(item);

        item = new CItem(
                new ItemCreator(Material.ENDER_EYE, (byte) 0)
                        .setName("§6Mondes Ressource End")
                        .setLores(Arrays.asList(
                                "§7",
                                "§fMondes de récolte de ressources dans l'end",
                                "§f(§aclaims disponibles§f)"
                        )))
                .setSlot(16);
        item.addEvent((cInventory, cItem, player, clickContext) -> {
            player.sendMessage(prefix + "Téléportation vers le §amonde ressource end§f.");
            player.teleport(Spawn.RESSOURCE_END.getLocation());
        });
        worldGUI.addElement(item);
    }

    private static void createWorlds(P i) {

        P.sender.sendMessage(prefix + "Creating worlds ...");

        Server server = i.getServer();

        WorldCreator wc = new WorldCreator(Spawn.LIBRE.getWorldName());
        wc.environment(World.Environment.NORMAL);
        wc.type(WorldType.NORMAL);
        server.createWorld(wc);

        wc = new WorldCreator(Spawn.LIBRE_NETHER.getWorldName());
        wc.environment(World.Environment.NETHER);
        wc.type(WorldType.NORMAL);
        server.createWorld(wc);

        wc = new WorldCreator(Spawn.LIBRE_END.getWorldName());
        wc.environment(World.Environment.THE_END);
        wc.type(WorldType.NORMAL);
        server.createWorld(wc);

        wc = new WorldCreator(Spawn.RESSOURCE.getWorldName());
        wc.environment(World.Environment.NORMAL);
        wc.type(WorldType.NORMAL);
        server.createWorld(wc);

        wc = new WorldCreator(Spawn.RESSOURCE_NETHER.getWorldName());
        wc.environment(World.Environment.NETHER);
        wc.type(WorldType.NORMAL);
        server.createWorld(wc);

        wc = new WorldCreator(Spawn.RESSOURCE_END.getWorldName());
        wc.environment(World.Environment.THE_END);
        wc.type(WorldType.NORMAL);
        server.createWorld(wc);

        P.sender.sendMessage(prefix + "World generated !");
    }

    public static CInventory getWorldGUI() {
        return worldGUI;
    }
}
