package fr.ChadOW.omegacore;

import fr.ChadOW.cinventory.CContent.CUtils;
import fr.ChadOW.omegacore.claim.Claim;
import fr.ChadOW.omegacore.claim.OmegaChunk;
import fr.ChadOW.omegacore.economie.Eco;
import fr.ChadOW.omegacore.global.Global;
import fr.ChadOW.omegacore.group.GroupManager;
import fr.ChadOW.omegacore.job.JobManager;
import fr.ChadOW.omegacore.shop.ShopManager;
import fr.ChadOW.omegacore.utils.ServerType;
import fr.ChadOW.omegacore.utils.hologram.HologramManager;
import fr.ChadOW.omegacore.utils.pluginmessage.PluginMessage;
import fr.ChadOW.omegacore.world.WorldManager;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class P extends JavaPlugin {

    private static final String prefix = "§6[Omega] §f";

    private static P INSTANCE;

    private ConsoleCommandSender sender;
    private Random random;

    private HologramManager hologramManager;
    private PluginMessage pluginMessage;
    private Eco eco;
    private WorldManager worldManager;
    private JobManager jobManager;
    private GroupManager groupManager;
    private ShopManager shopManager;

    @Override
    public void onEnable() {
        System.out.println(getPluginName() + " Launching ...");

        saveDefaultConfig();

        INSTANCE = this;
        sender = getServer().getConsoleSender();
        random = new Random();

        CUtils.init(this);
        ServerType.init(this);
        Global.init(this);
        pluginMessage = new PluginMessage(this);
        eco = new Eco(this);
        worldManager = new WorldManager(this);
        jobManager = new JobManager(this);
        if (ServerType.equals(ServerType.NORMAL))
            Claim.init(this);
        groupManager = new GroupManager(this);
        hologramManager = new HologramManager(this);
        shopManager = new ShopManager(this);

        System.out.println(getPluginName() + " Successfully launched");
    }

    @Override
    public void onDisable() {
        System.out.println(getName() + " Disabling ...");

        shopManager.saveShops();
        getHologramManager().saveHolograms();
        OmegaChunk.saveToDB();
    }

    public static P getInstance() {
        return INSTANCE;
    }

    public ConsoleCommandSender getSender() {
        return sender;
    }

    public Random getRandom() {
        return random;
    }

    public HologramManager getHologramManager() {
        return hologramManager;
    }

    public PluginMessage getPluginMessage() {
        return pluginMessage;
    }

    public Eco getEco() {
        return eco;
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }

    public JobManager getJobManager() {
        return jobManager;
    }

    public GroupManager getGroupManager() {
        return groupManager;
    }

    public ShopManager getShopManager() {
        return shopManager;
    }

    public String getPrefix() {
        return prefix;
    }

    public final String getPluginName() {
        return "[" + super.getName() + "]";
    }
}
