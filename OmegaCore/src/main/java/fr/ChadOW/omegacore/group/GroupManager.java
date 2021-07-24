package fr.ChadOW.omegacore.group;

import fr.ChadOW.omegacore.P;
import fr.ChadOW.omegacore.group.commands.GroupCommand;

public class GroupManager {
    public static final String prefix = "§6[§eGroupe§6] §f";

    public GroupManager(P i) {
        i.getCommand("group").setExecutor(new GroupCommand());
    }
}
