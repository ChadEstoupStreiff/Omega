package fr.ChadOW.omegacore.essentials.commands;

import fr.ChadOW.api.accounts.UserAccount;
import fr.ChadOW.api.enums.Rank;
import fr.ChadOW.bungee.Bungee;
import fr.ChadOW.omegacore.P;
import fr.ChadOW.omegacore.utils.omegaplayer.OmegaPlayer;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandNick implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player){
            if (UserAccount.getAccount(((Player) sender).getUniqueId()).getRank().getPower() >= Rank.LEGEND.getPower()) {
                if (args.length >= 1){
                    if (!NumberUtils.isNumber(args[0])){
                        if (args[0].length() >= 3){
                            if (args[0].length() <= 16){
                                OmegaPlayer omegaPlayer = P.getInstance().getOmegaPlayerManager().getOmegaPlayer(((Player) sender));
                                omegaPlayer.setNickName(args[0]);
                                //todo request to change nickname
                            }
                            else {
                                sender.sendMessage("Le pseudo est trop long");
                            }
                        }
                        else {
                            sender.sendMessage("Le pseudo est trop court");
                        }
                    }
                    else {
                        sender.sendMessage("Le pseudo ne peut pas être un nombre");
                    }
                }
                else {
                    sender.sendMessage("Aucun nouveau nom spécifié");
                }
            }
            else {
                sender.sendMessage("Vous n'avez pas la permission suffisante pour exécuter cette commande.");
            }
        }
        else {
            sender.sendMessage("Vous devez être un joueur pour exécuter cette commande.");
        }
        return true;
    }
}
