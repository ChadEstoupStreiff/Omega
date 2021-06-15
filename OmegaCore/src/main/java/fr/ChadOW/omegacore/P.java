package fr.ChadOW.omegacore;

import fr.ChadOW.cinventory.CContent.CUtils;
import fr.ChadOW.omegacore.claim.Claim;
import fr.ChadOW.omegacore.economie.Eco;
import fr.ChadOW.omegacore.global.Global;
import fr.ChadOW.omegacore.group.GroupManager;
import fr.ChadOW.omegacore.job.JobManager;
import fr.ChadOW.omegacore.utils.ServerType;
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

        INSTANCE = this;
        sender = getServer().getConsoleSender();
        random = new Random();

        CUtils.init(this);
        Eco.init(this);
        WorldManager.init(this);
        Global.init(this);
        JobManager.init(this);
        if (ServerType.equals(ServerType.NORMAL))
            Claim.init(this);
        GroupManager.init(this);

        System.out.println(name + ver + " Launched ...");
    }

    public static P getInstance() {
        return INSTANCE;
    }
}
