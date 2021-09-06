package fr.ChadOW.omegacore.essentials.commands;

import fr.ChadOW.api.accounts.UserAccount;
import fr.ChadOW.api.enums.Rank;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Objects;

public class CommandGamemode implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player){
            Player player = ((Player) sender);
            if (UserAccount.getAccount(player.getUniqueId()).getRank().getPower() >= Rank.ADMIN.getPower()){
                if (args.length == 0) {
                    player.sendMessage("Votre gamemode actuel est " + player.getGameMode());
                    //todo couleurs
                }
                else if (args.length == 1){
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null){
                        player.sendMessage("le gamemode de " + target.getGameMode() + " est " + target.getGameMode());
                    }
                    else {
                        if (NumberUtils.isNumber(args[0])) {
                            int gm = Integer.parseInt(args[0]);
                            if (gm == 0 || gm == 1 || gm == 2 || gm == 3)
                                player.setGameMode(Objects.requireNonNull(GameMode.getByValue(gm)));
                        }
                        else if (Arrays.stream(GameMode.values()).anyMatch(gm -> gm.toString().toLowerCase().equals(args[0]))){
                            player.setGameMode(GameMode.valueOf(args[0].toUpperCase()));
                        }
                        else {
                            // TODO: 06/09/2021 print doc
                        }
                    }
                }
                else if (args.length == 2){
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null){
                        if (NumberUtils.isNumber(args[1])) {
                            int gm = Integer.parseInt(args[1]);
                            if (gm == 0 || gm == 1 || gm == 2 || gm == 3)
                                target.setGameMode(Objects.requireNonNull(GameMode.getByValue(gm)));
                        }
                        else if (Arrays.stream(GameMode.values()).anyMatch(gm -> gm.toString().toLowerCase().equals(args[1]))){
                            target.setGameMode(GameMode.valueOf(args[1].toUpperCase()));
                        }
                    }
                    else {
                        // TODO: 06/09/2021 print doc
                    }
                }
            }
            else {
                //todo print doc
            }
        }
        else {
            //todo print doc
        }
        return true;
    }
}
