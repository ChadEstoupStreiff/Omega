package fr.ChadOW.omegacore.economie;

import fr.ChadOW.omegacore.P;
import fr.ChadOW.omegacore.economie.commands.CommandBank;
import fr.ChadOW.omegacore.economie.commands.CommandEco;
import fr.ChadOW.omegacore.economie.commands.CommandMoney;
import fr.ChadOW.omegacore.economie.commands.CommandPay;

public class Eco {

    public static final String prefix = "§6[Economie] §f";
    public static final String devise = "$";


    public static void init(P i) {
        i.getCommand("eco").setExecutor(new CommandEco());
        i.getCommand("money").setExecutor(new CommandMoney());
        i.getCommand("pay").setExecutor(new CommandPay());
        i.getCommand("bank").setExecutor(new CommandBank());
    }
}
