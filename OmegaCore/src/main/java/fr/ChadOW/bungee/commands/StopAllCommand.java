package fr.ChadOW.bungee.commands;

import fr.ChadOW.api.accounts.UserAccount;
import fr.ChadOW.api.enums.Rank;
import fr.ChadOW.bungee.Bungee;
import fr.ChadOW.omegacore.utils.NbrReader;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class StopAllCommand extends Command {
    public StopAllCommand() {
        super("stopall");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender == null || UserAccount.getAccount(((ProxiedPlayer) sender).getUniqueId()).getRank().getStaffPower() >= Rank.DEV.getStaffPower()) {
            if (args.length > 0) {
                if (NbrReader.isNumber(args[0])) {
                    Bungee.stop(Integer.parseInt(args[0]));
                }
            } else {
                Bungee.stop(5);
            }
        }
    }
}
