package fr.ChadOW.omegacore.global;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.ChadOW.api.accounts.UserAccount;
import fr.ChadOW.api.enums.Rank;
import fr.ChadOW.omegacore.P;
import fr.ChadOW.omegacore.utils.NbrReader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CommandStop implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender || UserAccount.getAccount(((Player) sender).getUniqueId()).getRank().getStaffPower() >= Rank.DEV.getStaffPower()) {
            if (args.length > 0) {
                if (NbrReader.isNumber(args[0])) {
                    stop(Integer.parseInt(args[0]));
                }
            } else {
                stop(5);
            }
        }
        return false;
    }

    private void stop(int i) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF("StopEverythingRequest");
        out.writeInt(i);

        P.getInstance().getServer().sendPluginMessage(P.getInstance(), "omega:pipe", out.toByteArray());
    }
}
