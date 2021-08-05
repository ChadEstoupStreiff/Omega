package fr.ChadOW.omegacore.world;

import fr.ChadOW.cinventory.CContent.CInventory;
import fr.ChadOW.cinventory.CContent.CItem;
import fr.ChadOW.cinventory.interfaces.ItemCreator;
import fr.ChadOW.omegacore.P;
import fr.ChadOW.omegacore.utils.ServerType;
import fr.ChadOW.omegacore.world.commands.CommandMonde;
import fr.ChadOW.omegacore.world.commands.CommandRTP;
import org.bukkit.*;

import java.util.Arrays;

public class WorldManager {

    private static CInventory worldGUI;

    public static final String prefix = "§6[Worlds] §f";

    public WorldManager(P i) {
        //createWorlds(i);
        createWorldGUI();

        i.getCommand("monde").setExecutor(new CommandMonde());
        if (ServerType.equals(ServerType.NORMAL) || ServerType.equals(ServerType.RESOURCE))
            i.getCommand("rtp").setExecutor(new CommandRTP());
    }

    private void createWorldGUI() {
        worldGUI = new CInventory(45, "§8§lServeur actuel: " + ServerType.getServerType());

        worldGUI.addElement(new CItem(new ItemCreator(Material.COMPASS, 0)
                .setName("§6Serveur libre")
                .setLores(Arrays.asList(
                        "§7",
                        "§fCliquez pour vous connecter au serveur"
                )))
                .addEvent((inventoryRepresentation, itemRepresentation, player, clickContext) -> {
                    inventoryRepresentation.close(player);
                    player.sendMessage(prefix + "Connexion vers §aserveur libre§f.");
                    P.getInstance().getPluginMessage().sendPlayerToServer(player, "claims");
                }).setSlot(11));
        worldGUI.addElement(new CItem(new ItemCreator(Material.DIAMOND_PICKAXE, 0)
                .setName("§6Serveur ressources")
                .setLores(Arrays.asList(
                        "§7",
                        "§fCliquez pour vous connecter au serveur"
                )))
                .addEvent((inventoryRepresentation, itemRepresentation, player, clickContext) -> {
                    inventoryRepresentation.close(player);
                    player.sendMessage(prefix + "Connexion vers §aserveur ressources§f.");
                    P.getInstance().getPluginMessage().sendPlayerToServer(player, "ressources");
                }).setSlot(13));
        worldGUI.addElement(new CItem(new ItemCreator(Material.GOLDEN_AXE, 0)
                .setName("§6Serveur mondes personnels")
                .setLores(Arrays.asList(
                        "§7",
                        "§fCliquez pour vous connecter au serveur"
                )))
                .addEvent((inventoryRepresentation, itemRepresentation, player, clickContext) -> {
                    inventoryRepresentation.close(player);
                    player.sendMessage(prefix + "Connexion vers §aserveur mondes personnels§f.");
                    P.getInstance().getPluginMessage().sendPlayerToServer(player, "worlds");
                }).setSlot(15));

        if (ServerType.equals(ServerType.NORMAL) || ServerType.equals(ServerType.RESOURCE)) {
            worldGUI.addElement(new CItem(new ItemCreator(Material.GRASS_BLOCK, 0)
                    .setName("§6OverWorld")
                    .setLores(Arrays.asList(
                            "§7",
                            "§fCliquez pour vous téléporter au spawn du monde normal"
                    )))
                    .addEvent((inventoryRepresentation, itemRepresentation, player, clickContext) -> {
                        inventoryRepresentation.close(player);
                        player.sendMessage(prefix + "Téléportation vers §amonde normal§f.");
                        player.teleport(Spawn.NORMAL.getLocation());
                    }).setSlot(29));
            worldGUI.addElement(new CItem(new ItemCreator(Material.NETHERRACK, 0)
                    .setName("§6Nether")
                    .setLores(Arrays.asList(
                            "§7",
                            "§fCliquez pour vous téléporter au spawn du monde nether"
                    )))
                    .addEvent((inventoryRepresentation, itemRepresentation, player, clickContext) -> {
                        inventoryRepresentation.close(player);
                        player.sendMessage(prefix + "Téléportation vers §amonde nether§f.");
                        player.teleport(Spawn.NETHER.getLocation());
                    }).setSlot(31));
            worldGUI.addElement(new CItem(new ItemCreator(Material.END_STONE, 0)
                    .setName("§6Ender")
                    .setLores(Arrays.asList(
                            "§7",
                            "§fCliquez pour vous téléporter au spawn du monde end"
                    )))
                    .addEvent((inventoryRepresentation, itemRepresentation, player, clickContext) -> {
                        inventoryRepresentation.close(player);
                        player.sendMessage(prefix + "Téléportation vers §amonde ender§f.");
                        player.teleport(Spawn.END.getLocation());
                    }).setSlot(33));
        } else
            worldGUI.addElement(new CItem(new ItemCreator(Material.BARRIER, 0).setName("§cIndisponible").setLores(Arrays.asList("§7", "§7cVous êtes sur un serveur spécial ne permettant", "§7pas de se téléporter entre les mondes"))));
        /*item = new CItem(
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

        P.sender.sendMessage(prefix + "World generated !");*/

        worldGUI.addElement(new CItem(new ItemCreator(Material.ORANGE_STAINED_GLASS_PANE, 0).setName("§f")).setSlot(0));
        worldGUI.addElement(new CItem(new ItemCreator(Material.ORANGE_STAINED_GLASS_PANE, 0).setName("§f")).setSlot(1));
        worldGUI.addElement(new CItem(new ItemCreator(Material.WHITE_STAINED_GLASS_PANE, 0).setName("§f")).setSlot(2));
        worldGUI.addElement(new CItem(new ItemCreator(Material.ORANGE_STAINED_GLASS_PANE, 0).setName("§f")).setSlot(3));
        worldGUI.addElement(new CItem(new ItemCreator(Material.WHITE_STAINED_GLASS_PANE, 0).setName("§f")).setSlot(4));
        worldGUI.addElement(new CItem(new ItemCreator(Material.ORANGE_STAINED_GLASS_PANE, 0).setName("§f")).setSlot(5));
        worldGUI.addElement(new CItem(new ItemCreator(Material.WHITE_STAINED_GLASS_PANE, 0).setName("§f")).setSlot(6));
        worldGUI.addElement(new CItem(new ItemCreator(Material.ORANGE_STAINED_GLASS_PANE, 0).setName("§f")).setSlot(7));
        worldGUI.addElement(new CItem(new ItemCreator(Material.ORANGE_STAINED_GLASS_PANE, 0).setName("§f")).setSlot(8));

        worldGUI.addElement(new CItem(new ItemCreator(Material.WHITE_STAINED_GLASS_PANE, 0).setName("§f")).setSlot(9));
        worldGUI.addElement(new CItem(new ItemCreator(Material.WHITE_STAINED_GLASS_PANE, 0).setName("§f")).setSlot(17));

        worldGUI.addElement(new CItem(new ItemCreator(Material.ORANGE_STAINED_GLASS_PANE, 0).setName("§f")).setSlot(18));
        worldGUI.addElement(new CItem(new ItemCreator(Material.ORANGE_STAINED_GLASS_PANE, 0).setName("§f")).setSlot(19));
        worldGUI.addElement(new CItem(new ItemCreator(Material.WHITE_STAINED_GLASS_PANE, 0).setName("§f")).setSlot(20));
        worldGUI.addElement(new CItem(new ItemCreator(Material.ORANGE_STAINED_GLASS_PANE, 0).setName("§f")).setSlot(21));
        worldGUI.addElement(new CItem(new ItemCreator(Material.WHITE_STAINED_GLASS_PANE, 0).setName("§f")).setSlot(22));
        worldGUI.addElement(new CItem(new ItemCreator(Material.ORANGE_STAINED_GLASS_PANE, 0).setName("§f")).setSlot(23));
        worldGUI.addElement(new CItem(new ItemCreator(Material.WHITE_STAINED_GLASS_PANE, 0).setName("§f")).setSlot(24));
        worldGUI.addElement(new CItem(new ItemCreator(Material.ORANGE_STAINED_GLASS_PANE, 0).setName("§f")).setSlot(25));
        worldGUI.addElement(new CItem(new ItemCreator(Material.ORANGE_STAINED_GLASS_PANE, 0).setName("§f")).setSlot(26));

        worldGUI.addElement(new CItem(new ItemCreator(Material.WHITE_STAINED_GLASS_PANE, 0).setName("§f")).setSlot(27));
        worldGUI.addElement(new CItem(new ItemCreator(Material.WHITE_STAINED_GLASS_PANE, 0).setName("§f")).setSlot(35));

        worldGUI.addElement(new CItem(new ItemCreator(Material.ORANGE_STAINED_GLASS_PANE, 0).setName("§f")).setSlot(36));
        worldGUI.addElement(new CItem(new ItemCreator(Material.ORANGE_STAINED_GLASS_PANE, 0).setName("§f")).setSlot(37));
        worldGUI.addElement(new CItem(new ItemCreator(Material.WHITE_STAINED_GLASS_PANE, 0).setName("§f")).setSlot(38));
        worldGUI.addElement(new CItem(new ItemCreator(Material.ORANGE_STAINED_GLASS_PANE, 0).setName("§f")).setSlot(39));
        worldGUI.addElement(new CItem(new ItemCreator(Material.WHITE_STAINED_GLASS_PANE, 0).setName("§f")).setSlot(40));
        worldGUI.addElement(new CItem(new ItemCreator(Material.ORANGE_STAINED_GLASS_PANE, 0).setName("§f")).setSlot(41));
        worldGUI.addElement(new CItem(new ItemCreator(Material.WHITE_STAINED_GLASS_PANE, 0).setName("§f")).setSlot(42));
        worldGUI.addElement(new CItem(new ItemCreator(Material.ORANGE_STAINED_GLASS_PANE, 0).setName("§f")).setSlot(43));
        worldGUI.addElement(new CItem(new ItemCreator(Material.ORANGE_STAINED_GLASS_PANE, 0).setName("§f")).setSlot(44));
    }

    public CInventory getWorldGUI() {
        return worldGUI;
    }
}
