package fr.ChadOW.bungee;

import fr.ChadOW.bungee.commands.StopCommand;
import fr.ChadOW.bungee.eco.Taxes;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.concurrent.TimeUnit;

public class Bungee extends Plugin {
    private static Bungee instance;

    public static Bungee getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        getProxy().registerChannel("omega:pipe");
        getProxy().getPluginManager().registerListener(this, new BungeeListener());
        getProxy().getPluginManager().registerCommand(this, new StopCommand());
        getProxy().getScheduler().schedule(this, () -> {
            Taxes.init(this);
        }, 1, TimeUnit.SECONDS);
    }

    public static void stop(int i) {
        if (i == 0) {
            getInstance().getProxy().broadcast(new TextComponent("§6[NETWORK] §4Extinction des serveurs !"));
            //TODO Request all to shutdown
            getInstance().getProxy().stop();
        } else {
            getInstance().getProxy().getScheduler().schedule(getInstance(), () -> {
                getInstance().getProxy().broadcast(new TextComponent("§6[NETWORK] §cExtinction des serveurs dans " + i + " minutes !"));
                stop(i -1);
            }, 1, TimeUnit.MINUTES);
        }
    }
}
