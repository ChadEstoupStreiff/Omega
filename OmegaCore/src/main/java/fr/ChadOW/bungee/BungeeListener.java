package fr.ChadOW.bungee;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import fr.ChadOW.api.accounts.UserAccount;
import net.md_5.bungee.api.chat.TextComponent;
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

            switch (subChannel) {
                case "PlayerGlobalMessage":
                    String name = event.getReceiver().toString();
                    UUID uuid = UUID.fromString(in.readUTF());
                    String message = in.readUTF();
                    UserAccount account = UserAccount.getAccount(uuid);

                    Bungee.getInstance().getProxy().broadcast(new TextComponent(
                            account.getRank().getPrefix() + name + account.getRank().getSuffix() + message
                    ));
                    break;
                case "GlobalMessage":
                    Bungee.getInstance().getProxy().broadcast(new TextComponent(in.readUTF()));
                    break;
            }
        }
    }
}
