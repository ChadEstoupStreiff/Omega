package fr.ChadOW.omegacore.essentials;

import fr.ChadOW.omegacore.P;
import fr.ChadOW.omegacore.essentials.commands.*;

import java.util.Objects;

public class CommandRegister {

    public CommandRegister(P i) {
        Objects.requireNonNull(i.getCommand("back")).setExecutor(new CommandBack());
        Objects.requireNonNull(i.getCommand("enchant")).setExecutor(new CommandEnchant());
        Objects.requireNonNull(i.getCommand("feed")).setExecutor(new CommandFeed());
        Objects.requireNonNull(i.getCommand("fly")).setExecutor(new CommandFly());
        Objects.requireNonNull(i.getCommand("flyspeed")).setExecutor(new CommandFlySpeed());
        Objects.requireNonNull(i.getCommand("gamemode")).setExecutor(new CommandGamemode());
        Objects.requireNonNull(i.getCommand("getpos")).setExecutor(new CommandGetPos());
        Objects.requireNonNull(i.getCommand("god")).setExecutor(new CommandGod());
        Objects.requireNonNull(i.getCommand("heal")).setExecutor(new CommandHeal());
        Objects.requireNonNull(i.getCommand("kill")).setExecutor(new CommandKill());
        Objects.requireNonNull(i.getCommand("list")).setExecutor(new CommandList());
        Objects.requireNonNull(i.getCommand("mail")).setExecutor(new CommandMail());
        Objects.requireNonNull(i.getCommand("nick")).setExecutor(new CommandNick());
        Objects.requireNonNull(i.getCommand("powertool")).setExecutor(new CommandPowertool());
        Objects.requireNonNull(i.getCommand("repair")).setExecutor(new CommandRepair());
        Objects.requireNonNull(i.getCommand("sky")).setExecutor(new CommandSky());
        Objects.requireNonNull(i.getCommand("speed")).setExecutor(new CommandSpeed());
        Objects.requireNonNull(i.getCommand("storm")).setExecutor(new CommandStorm());
        Objects.requireNonNull(i.getCommand("suicide")).setExecutor(new CommandSuicide());
        Objects.requireNonNull(i.getCommand("sun")).setExecutor(new CommandSun());
        Objects.requireNonNull(i.getCommand("top")).setExecutor(new CommandTop());

    }

}
