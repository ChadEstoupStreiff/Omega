package fr.ChadOW.api.bungee.listeners;

import com.google.gson.Gson;
import com.google.gson.internal.bind.SqlDateTypeAdapter;
import fr.ChadOW.api.accounts.BankAccount;
import fr.ChadOW.api.accounts.JobAccount;
import fr.ChadOW.api.accounts.UserAccount;
import fr.ChadOW.api.bungee.BungeeAPI;
import fr.ChadOW.api.managers.JedisManager;
import fr.ChadOW.api.managers.SQLManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import redis.clients.jedis.Jedis;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;


public class PlayerJoinQuit implements Listener {
    private final BungeeAPI plugin;

    public PlayerJoinQuit(BungeeAPI plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(LoginEvent event) {
        setup(event.getConnection().getUniqueId());
    }

    @EventHandler
    public void PostJoin(PostLoginEvent event) {
        saveDatasOfPlayer(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent event) {
        save(event.getPlayer().getUniqueId());
    }


    public void setup(UUID uuid) {
        ProxyServer.getInstance().getScheduler().runAsync(this.plugin, () -> {
            UserAccount userAccount = UserAccount.getAccountFromDb(uuid);
            BankAccount.getAccountFromDb(userAccount.getBankID());
            JobAccount.getAccountFromDb(userAccount.getJobID());
            System.out.println("[OmegaAPI] Datas of " + uuid + " loaded from DB");
        });
    }

    public void save(UUID uuid) {
        ProxyServer.getInstance().getScheduler().runAsync(this.plugin, () -> {
            UserAccount userAccount = UserAccount.getAccount(uuid);
            userAccount.sendToDb();
            userAccount.getBankAccount().sendToDb();
            userAccount.getJobAccount().sendToDb();
            System.out.println("[OmegaAPI] Datas of " + uuid + " saved to DB");
        });
    }

    private void saveDatasOfPlayer(ProxiedPlayer player) {
        UUID uuid = player.getUniqueId();

        SQLManager sql = SQLManager.getInstance();
        Gson gson = JedisManager.getGson();

        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss"));
        String playerName = player.getName();
        String playerIP = player.getAddress().getAddress().toString();

        sql.query("SELECT listNames, listIps FROM DATAS WHERE uuid='" + uuid + "'", rs ->  {
            try {
                if (rs.next()) {
                    ArrayList<String> nameList = gson.fromJson(rs.getString("listNames"), ArrayList.class);
                    if (!nameList.contains(playerName))
                        nameList.add(playerName);
                    ArrayList<String> ipList = gson.fromJson(rs.getString("listIPs"), ArrayList.class);
                    if (!ipList.contains(playerIP))
                        ipList.add(playerIP);

                    sql.update("UPDATE DATAS SET " +
                            "lastConnection='" + date + "', " +
                            "lastName='" + playerName + "', " +
                            "lastIP='" + playerIP + "', " +
                            "listNames='" + gson.toJson(nameList) + "', " +
                            "listIPs='" + gson.toJson(ipList) + "' " +
                            "WHERE uuid='" + uuid + "'");
                } else {
                    sql.update("INSERT INTO DATAS (uuid, firstConnection, lastConnection, lastName, lastIP, listNames, listIPs) VALUES " +
                            "('" +
                            uuid + "', '" +
                            date + "', '" +
                            date + "', '" +
                            playerName + "', '" +
                            playerIP + "', '" +
                            gson.toJson(new ArrayList<>(Collections.singletonList(playerName))) + "', '" +
                            gson.toJson(new ArrayList<>(Collections.singletonList(playerIP))) + "')"
                    );
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }
}