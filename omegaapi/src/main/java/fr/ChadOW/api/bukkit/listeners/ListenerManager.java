package fr.ChadOW.api.bukkit.listeners;

import fr.ChadOW.api.bukkit.BukkitAPI;
import fr.ChadOW.api.bukkit.chat.OmegaChatEvent;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;

public class ListenerManager {
    private static PluginManager manager;

    public static void init(BukkitAPI i) {
        manager = i.getServer().getPluginManager();

        manager.registerEvents(new OmegaChatListener(), i);
        manager.registerEvents(new GlobalListener(), i);
    }

    public static void callEvent(Event event) {
        manager.callEvent(event);
    }
}
