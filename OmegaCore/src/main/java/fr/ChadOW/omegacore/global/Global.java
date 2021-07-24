package fr.ChadOW.omegacore.global;

import fr.ChadOW.omegacore.P;
import fr.ChadOW.omegacore.global.bukkit.CommandAccount;
import fr.ChadOW.omegacore.global.bukkit.CommandSetRank;
import fr.ChadOW.omegacore.global.bukkit.GlobalListener;

public class Global {

    public static void init(P i) {
        i.getCommand("setrank").setExecutor(new CommandSetRank());
        i.getCommand("account").setExecutor(new CommandAccount());
        //i.getCommand("stop").setExecutor(new CommandStop());
        i.getServer().getPluginManager().registerEvents(new GlobalListener(), i);
    }
}
