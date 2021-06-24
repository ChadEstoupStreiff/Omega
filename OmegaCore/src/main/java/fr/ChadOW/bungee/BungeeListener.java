package fr.ChadOW.bungee;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import fr.ChadOW.api.accounts.UserAccount;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;

public class BungeeListener implements Listener {

    @EventHandler
    public void on(PluginMessageEvent event) {
        if (event.getTag().equals("omega:pipe")) {
            ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());
            String subChannel = in.readUTF();

            String name, message;
            switch (subChannel) {
                case "PlayerGlobalMessage":
                    name = event.getReceiver().toString();
                    UserAccount account = UserAccount.getAccount(UUID.fromString(in.readUTF()));
                    message = in.readUTF();

                    Bungee.getInstance().getProxy().broadcast(new TextComponent(
                            account.getRank().getPrefix() + name + account.getRank().getSuffix() + message
                    ));
                    break;
                case "GlobalMessage":
                    Bungee.getInstance().getProxy().broadcast(new TextComponent(in.readUTF()));
                    break;
                case "SendToServer":
                    ProxiedPlayer player = Bungee.getInstance().getProxy().getPlayer(event.getReceiver().toString());
                    ServerInfo serverInfo = Bungee.getInstance().getProxy().getServerInfo(in.readUTF());

                    if (player != null) {
                        if (serverInfo != null)
                            player.connect(serverInfo);
                        else
                            player.sendMessage(new TextComponent("§cErreur lors de la connexion au serveur ... veuillez réessayer"));
                    }
                    break;
                case "StopEverythingRequest":
                    Bungee.stop(in.readInt());
                    break;
            }
        }
    }
}
