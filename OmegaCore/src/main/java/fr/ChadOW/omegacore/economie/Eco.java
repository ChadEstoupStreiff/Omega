package fr.ChadOW.omegacore.economie;

import fr.ChadOW.omegacore.P;
import fr.ChadOW.omegacore.economie.commands.*;

public class Eco {

    public static final String prefix = "§6[Economie] §f";
    public static final String devise = "$";


    public Eco(P i) {
        i.getCommand("eco").setExecutor(new CommandEco());
        i.getCommand("money").setExecutor(new CommandMoney());
        i.getCommand("pay").setExecutor(new CommandPay());
        i.getCommand("rank").setExecutor(new CommandRank());
    }
}
