package fr.ChadOW.omegacore.claim.commands;

import fr.ChadOW.omegacore.P;
import fr.ChadOW.omegacore.claim.ClaimManager;
import fr.ChadOW.omegacore.claim.OmegaChunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnClaimCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            OmegaChunk chunk = P.getInstance().getClaimManager().getChunk(player.getLocation());
            if (chunk.getOwnerID() != null && chunk.getOwnerID().equalsIgnoreCase(player.getUniqueId().toString())) {
                chunk.setOwnerID(null);
                player.sendMessage(P.getInstance().getClaimManager().prefix + "§cVous n'êtes plus propriétaire de cette terre.");
            } else {
                player.sendMessage(P.getInstance().getClaimManager().prefix + "§cCette portion de terre ne vous appartient pas.");
            }
        }
        return true;
    }
}
