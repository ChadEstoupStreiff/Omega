package fr.ChadOW.bungee;

import fr.ChadOW.bungee.eco.Taxes;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Plugin;

public class Bungee extends Plugin {
    private static Bungee instance;

    public static Bungee getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        Taxes.init(this);
        getProxy().registerChannel("omega:pipe");
        getProxy().getPluginManager().registerListener(this, new BungeeListener());
    }
}
