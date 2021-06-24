package fr.ChadOW.omegacore.claim.commands;

import fr.ChadOW.api.managers.OmegaAPIUtils;
import fr.ChadOW.omegacore.claim.Claim;
import fr.ChadOW.omegacore.claim.OmegaChunk;
import fr.ChadOW.omegacore.utils.OmegaUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ClaimCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("list")) {
                    List<OmegaChunk> chunks = OmegaChunk.getOmegaChunk(player.getUniqueId().toString());
                    player.sendMessage(Claim.prefix + "Vos portions de terre:");
                    chunks.forEach(chunk -> player.sendMessage("  §f- " + chunk));
                } else if (args[0].equalsIgnoreCase("show")) {
                    Claim.switchShow(player);
                }else if (args[0].equalsIgnoreCase("map")){
                    Claim.getMap(player.getLocation(), 15, 15, player.getUniqueId().toString()).forEach(line -> player.spigot().sendMessage(line));
                } else if (args[0].equalsIgnoreCase("claim")) {
                    OmegaChunk chunk = OmegaChunk.getChunk(player.getLocation());
                    if (chunk.getOwnerID() == null)
                        OmegaUtils.confirmBeforeExecute(
                                player,
                                "§eClaim ? (" + chunk.getX() + ";" + chunk.getY() + ")",
                                null,
                                null,
                                null,
                                (cInventory, cItem, player1, clickContext) -> {
                                    chunk.setOwnerID(player.getUniqueId().toString());
                                    player.sendMessage(Claim.prefix + "Cette portion de terre vous appartient désormais");
                                });
                    else
                        player.sendMessage(Claim.prefix + "Cette portion de terre appartient à " + OmegaAPIUtils.tryToConvertIDToStringByUserAccount(chunk.getOwnerID()));
                }
            } else {
                OmegaChunk chunk = OmegaChunk.getChunk(player.getLocation());
                player.sendMessage(Claim.prefix + "Vous êtes sur la portion de terre: §e" + chunk);
            }
        }
        return true;
    }
}
