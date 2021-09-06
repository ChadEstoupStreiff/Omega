package fr.ChadOW.omegacore.economie;

import fr.ChadOW.omegacore.P;
import fr.ChadOW.omegacore.economie.commands.*;

import java.util.Objects;

public class Eco {

    public static final String prefix = "§6[Economie] §f";
    public static final String devise = "$";


    public Eco(P i) {
        Objects.requireNonNull(i.getCommand("eco")).setExecutor(new CommandEco());
        Objects.requireNonNull(i.getCommand("money")).setExecutor(new CommandMoney());
        Objects.requireNonNull(i.getCommand("pay")).setExecutor(new CommandPay());
        Objects.requireNonNull(i.getCommand("rank")).setExecutor(new CommandRank());
    }
}
