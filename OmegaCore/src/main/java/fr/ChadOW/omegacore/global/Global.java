package fr.ChadOW.omegacore.global;

import fr.ChadOW.omegacore.P;
import fr.ChadOW.omegacore.global.commands.CommandAccount;
import fr.ChadOW.omegacore.global.commands.CommandHelp;
import fr.ChadOW.omegacore.global.commands.CommandSetRank;

public class Global {

    public static void init(P i) {
        i.getCommand("setrank").setExecutor(new CommandSetRank());
        i.getCommand("account").setExecutor(new CommandAccount());
        i.getCommand("help").setExecutor(new CommandHelp());
        //i.getCommand("stop").setExecutor(new CommandStop());
        i.getServer().getPluginManager().registerEvents(new GlobalListener(), i);
    }
}
