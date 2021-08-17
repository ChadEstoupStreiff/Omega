package fr.ChadOW.omegacore.group.commands;

import fr.ChadOW.api.accounts.UserAccount;
import fr.ChadOW.api.accounts.group.Group;
import fr.ChadOW.api.accounts.group.Member;
import fr.ChadOW.api.managers.OmegaAPIUtils;
import fr.ChadOW.omegacore.group.GroupManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GroupCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("create")) {
                    if (args.length > 1) {
                        UserAccount userAccount = UserAccount.getAccount(player.getUniqueId());
                        Group group = userAccount.getGroup();
                        if (group != null) {
                            player.sendMessage(GroupManager.prefix + "Vous être dans un groupe.");
                        } else {
                            userAccount.setGroup(
                                    new Group(
                                            "G" + player.getUniqueId(),
                                            args[1],
                                            "G" + player.getUniqueId(),
                                            new Member(player.getUniqueId(), Member.HIERARCHY.LEADER),
                                            new ArrayList<>()));
                            player.sendMessage(GroupManager.prefix + "Groupe créé avec succes !");
                        }
                    } else {
                        player.sendMessage(GroupManager.prefix + "Préciser un nom de groupe");
                    }
                } else {
                    player.sendMessage(GroupManager.prefix + ChatColor.GOLD + "Pour créer un groupe faites /" + cmd.getName() + " create");
                }
            } else {
                Group group = UserAccount.getAccount(player.getUniqueId()).getGroup();
                if (group == null) {
                    player.sendMessage(GroupManager.prefix + "Vous n'avez pas de groupe.");
                } else {
                    player.sendMessage(GroupManager.prefix + "Groupe: " + group.getName());
                    player.sendMessage("Chef:" + OmegaAPIUtils.tryToConvertIDToStringByUserAccount(group.getChef().getUuid().toString()));
                    player.sendMessage("Solde: " + group.getBankAccount().getAmount());
                    player.sendMessage("Membres:");
                    for (Member member : group.getMembers()) {
                        player.sendMessage(" - " + member.getHierarchy() + " > " + OmegaAPIUtils.tryToConvertIDToStringByUserAccount(member.getUuid().toString()));
                    }
                }
            }
        }
        return true;
    }
}
