package fr.ChadOW.omegacore;

import fr.ChadOW.cinventory.CContent.CUtils;
import fr.ChadOW.omegacore.claim.Claim;
import fr.ChadOW.omegacore.claim.OmegaChunk;
import fr.ChadOW.omegacore.economie.Eco;
import fr.ChadOW.omegacore.global.Global;
import fr.ChadOW.omegacore.group.GroupManager;
import fr.ChadOW.omegacore.job.JobManager;
import fr.ChadOW.omegacore.utils.ServerType;
import fr.ChadOW.omegacore.utils.hologram.Hologram;
import fr.ChadOW.omegacore.utils.pluginmessage.PluginMessage;
import fr.ChadOW.omegacore.world.WorldManager;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class P extends JavaPlugin {

    public static final String name = "OmegaCore";
    public static final String ver = "V1.0";

    public static P INSTANCE;

    public static ConsoleCommandSender sender;
    public static Random random;

    @Override
    public void onEnable() {
        System.out.println(name + ver + " Launching ...");

        saveDefaultConfig();

        INSTANCE = this;
        sender = getServer().getConsoleSender();
        random = new Random();

        PluginMessage.init(this);
        ServerType.init(this);
        CUtils.init(this);
        Eco.init(this);
        WorldManager.init(this);
        Global.init(this);
        JobManager.init(this);
        if (ServerType.equals(ServerType.NORMAL))
            Claim.init(this);
        GroupManager.init(this);
        Hologram.init(this);

        System.out.println(name + ver + " Launched");
    }

    @Override
    public void onDisable() {
        System.out.println(name + ver + " Disabling ...");

        Hologram.disable(this);
        OmegaChunk.saveToDB();

        System.out.println(name + ver + " Disabled");
    }

    public static P getInstance() {
        return INSTANCE;
    }

    public static ConsoleCommandSender getSender() {
        return sender;
    }

    public static Random getRandom() {
        return random;
    }
}
