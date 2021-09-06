package fr.ChadOW.omegacore.global;

import fr.ChadOW.omegacore.P;
import fr.ChadOW.omegacore.global.commands.CommandAccount;
import fr.ChadOW.omegacore.global.commands.CommandHelp;
import fr.ChadOW.omegacore.global.commands.CommandSetRank;

import java.util.Objects;

public class Global {

    public static void init(P i) {
        Objects.requireNonNull(i.getCommand("setrank")).setExecutor(new CommandSetRank());
        Objects.requireNonNull(i.getCommand("account")).setExecutor(new CommandAccount());
        Objects.requireNonNull(i.getCommand("help")).setExecutor(new CommandHelp());
        i.getServer().getPluginManager().registerEvents(new GlobalListener(), i);
    }
}
