package fr.ChadOW.omegacore;

import fr.ChadOW.api.accounts.UserAccount;
import fr.ChadOW.api.managers.JedisManager;
import fr.ChadOW.api.managers.OmegaAPIUtils;
import fr.ChadOW.api.managers.SQLManager;
import fr.ChadOW.bridge.Bridge;
import fr.ChadOW.cinventory.CContent.CUtils;
import fr.ChadOW.omegacore.claim.ClaimManager;
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
import org.bukkit.entity.Player;
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
    private ClaimManager claimManager;
    private Bridge bridge;

    @Override
    public void onEnable() {
        System.out.println(getPluginName() + " Launching ...");

        INSTANCE = this;

        saveDefaultConfig();
        sender = getServer().getConsoleSender();
        random = new Random();

        initMySQL();
        initRedis();
        getServer().getScheduler().runTaskTimer(this, OmegaAPIUtils::getData, 6000, 6000);


        CUtils.init(this);
        ServerType.init(this);
        Global.init(this);
        bridge = new Bridge(this);
        eco = new Eco(this);
        worldManager = new WorldManager(this);
        jobManager = new JobManager(this);
        if (ServerType.equals(ServerType.NORMAL))
            claimManager = new ClaimManager(this);
        groupManager = new GroupManager(this);
        hologramManager = new HologramManager(this);
        shopManager = new ShopManager(this);

        System.out.println(getPluginName() + " Successfully launched");
    }

    @Override
    public void onDisable() {
        System.out.println(getName() + " Disabling ...");

        getShopManager().saveShops();
        getHologramManager().saveHolograms();

        if (ServerType.equals(ServerType.NORMAL))
            getClaimManager().save();

        JedisManager.getInstance().poolJedis.close();
        SQLManager.getInstance().closePool();
    }

    public static void resetDisplay(Player player) {
        UserAccount account = UserAccount.getAccount(player.getUniqueId());

        player.setPlayerListName(account.getRank().getTab() + player.getDisplayName());
    }

    private void initRedis() {
        new JedisManager(getConfig().getString("redis.hostname"), getConfig().getInt("redis.port"), getConfig().getString("redis.password"));
    }

    private void initMySQL() {
        new SQLManager(getConfig().getString("mysql.hostname"), getConfig().getInt("mysql.port"), getConfig().getString("mysql.database"), getConfig().getString("mysql.user"), getConfig().getString("mysql.password"));
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

    public ClaimManager getClaimManager() {
        return claimManager;
    }
}
