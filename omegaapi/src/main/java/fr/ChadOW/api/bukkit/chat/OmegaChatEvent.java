package fr.ChadOW.api.bukkit.chat;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class OmegaChatEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    public AsyncPlayerChatEvent basicChatEvent;
    public String message;

    private boolean canceled;

    public OmegaChatEvent(AsyncPlayerChatEvent event, String message) {
        canceled = false;
        this.basicChatEvent = event;
        this.message = message;
    }

    public void setCanceled(boolean bool) {
        this.canceled = bool;
    }

    public boolean isCanceled() {
        return canceled;
    }

    @Override
    public HandlerList getHandlers() { return handlers; }
    public static HandlerList getHandlerList() { return handlers; }
}
