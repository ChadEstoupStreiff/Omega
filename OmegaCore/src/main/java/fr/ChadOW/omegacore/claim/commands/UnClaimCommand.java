package fr.ChadOW.omegacore.claim.commands;

import fr.ChadOW.omegacore.claim.Claim;
import fr.ChadOW.omegacore.claim.OmegaChunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnClaimCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            OmegaChunk chunk = OmegaChunk.getChunk(player.getLocation());
            if (chunk.getOwnerID() != null && chunk.getOwnerID().equalsIgnoreCase(player.getUniqueId().toString())) {
                chunk.setOwnerID(null);
                player.sendMessage(Claim.prefix + "§cVous n'êtes plus propriétaire de cette terre.");
            } else {
                player.sendMessage(Claim.prefix + "§cCette portion de terre ne vous appartient pas.");
            }
        }
        return true;
    }
}
