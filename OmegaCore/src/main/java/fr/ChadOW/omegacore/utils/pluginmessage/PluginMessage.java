package fr.ChadOW.omegacore.utils.pluginmessage;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.ChadOW.omegacore.P;
import org.bukkit.entity.Player;

public class PluginMessage {
    public void sendPlayerChatMessage(Player player, String message) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF("PlayerGlobalMessage");
        out.writeUTF(player.getUniqueId().toString());
        out.writeUTF(message);

        player.sendPluginMessage(P.getInstance(), "omega:pipe", out.toByteArray());
    }

    public void sendGlobalMessage(String message) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF("GlobalMessage");
        out.writeUTF(message);

        P.getInstance().getServer().sendPluginMessage(P.getInstance(), "omega:pipe", out.toByteArray());
    }

    public void sendPlayerToServer(Player player, String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF("SendToServer");
        out.writeUTF(server);

        player.sendPluginMessage(P.getInstance(), "omega:pipe", out.toByteArray());
    }
}
