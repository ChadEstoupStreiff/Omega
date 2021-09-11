package fr.ChadOW.bridge;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import fr.ChadOW.bungee.Bungee;
import fr.ChadOW.omegacore.P;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Bridge implements PluginMessageListener, Listener {
    private final List<RequestListener> listeners = new LinkedList<>();
    private final List<Request> requests = new LinkedList<>();

    public Bridge(P p) {
        p.getServer().getMessenger().registerOutgoingPluginChannel(p, "omega:pipe");
        p.getServer().getMessenger().registerIncomingPluginChannel(p, "omega:pipe", this);
    }

    public Bridge(Bungee bungee) {
        bungee.getProxy().registerChannel("omega:pipe");
        bungee.getProxy().getPluginManager().registerListener(bungee, this);
    }

    public void sendRequest(Request request, Player player) {
        requests.add(request);
        send(request, player);
    }
    public void send(Bridgeable toSend, Player player) {
        player.sendPluginMessage(P.getInstance(), "omega:pipe", toSend.createBytes().toByteArray());
    }

    public void sendRequest(Request request) {
        requests.add(request);
        send(request);
    }

    public void send(Bridgeable toSend) {
        P.getInstance().getServer().sendPluginMessage(P.getInstance(), "omega:pipe", toSend.createBytes().toByteArray());
    }

    public void registerListener(RequestListener requestListener) {
        listeners.add(requestListener);
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {

        if (channel.equals("omega:pipe")) {
            ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
            String subchannel = in.readUTF();

            if (subchannel.startsWith("request_") || subchannel.startsWith("answer_request_"))
                processData(in, subchannel);
        }
    }

    private void processData(ByteArrayDataInput in, String ID) {
        List<String> data = new ArrayList<>();
        if (ID.startsWith("answer_")) {
            String requestID = ID.substring("answer_".length());
            String line;
            do {
                line = in.readUTF();
                if (!line.equals("END"))
                    data.add(line);
            } while (!line.equals("END"));

            for (Request request : requests) {
                if (request.getID().equals(requestID)) {
                    requests.remove(request);
                    request.getRunnable().run(request, data);
                    break;
                }
            }
        }

        for (RequestListener listener : listeners) {
            listener.tryRun(data);
        }
    }


    @EventHandler
    public void on(PluginMessageEvent event) {
        if (event.getTag().equals("omega:pipe")) {

        }
    }
}
