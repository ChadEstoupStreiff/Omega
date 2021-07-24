package fr.ChadOW.omegacore.utils.hologram;

import fr.ChadOW.api.accounts.UserAccount;
import fr.ChadOW.api.enums.Rank;
import fr.ChadOW.omegacore.P;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class CommandHologram implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (UserAccount.getAccount(player.getUniqueId()).getRank().getStaffPower() >= Rank.DEV.getStaffPower()) {
                if (args.length == 0) {
                    //TODO print DOC
                    Hologram hologram = P.getInstance().getHologramManager().createHologram("test", player.getLocation(), Arrays.asList("§cPremière ligne", "§bSeconde ligne", "§aTo delete"));
                    hologram.insertLine("§dInsert", 1);
                    hologram.removeLine(3);
                }
            }
        }
        return true;
    }
}
