package fr.ChadOW.omegacore.utils;

import fr.ChadOW.cinventory.CContent.CInventory;
import fr.ChadOW.cinventory.CContent.CItem;
import fr.ChadOW.cinventory.ItemCreator;
import fr.ChadOW.cinventory.events.ItemClickEvent;
import fr.ChadOW.omegacore.P;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class OmegaUtils {
    public static double roundDouble(double a) {
        return (int)(a*100)/100d;
    }

    /**
     *
     * Confirm before execution a specific item
     *
     * @param player
     * @param title
     * @param cancelLore
     * @param confirmLore
     * @param cancelEvent
     * @param confirmEvent
     */
    public static void confirmBeforeExecute(Player player, String title, List<String> cancelLore, List<String> confirmLore, ItemClickEvent cancelEvent, ItemClickEvent confirmEvent) {
        CInventory inv = new CInventory(9, title);

        CItem item = new CItem(new ItemCreator(Material.RED_WOOL, 0).setName("§4ANNULER")).setSlot(3);
        confirmBeforeExecuteItem(player, cancelLore, cancelEvent, inv, item);

        item = new CItem(new ItemCreator(Material.GREEN_WOOL, 0).setName("§aCONFIRMER")).setSlot(5);
        confirmBeforeExecuteItem(player, confirmLore, confirmEvent, inv, item);
        inv.open(player);
    }

    /**
     *
     * Confirm before execution a cancellable item
     *
     * @param player
     * @param cancelLore
     * @param cancelEvent
     * @param inv
     * @param item
     */
    private static void confirmBeforeExecuteItem(Player player, List<String> cancelLore, ItemClickEvent cancelEvent, CInventory inv, CItem item) {
        if (cancelLore != null)
            item.setDescription(cancelLore);
        item.addEvent((cInventory, cItem, player1, clickContext) -> {
            inv.close(player);
        });
        if (cancelEvent != null)
            item.addEvent(cancelEvent);
        inv.addElement(item);
    }

    public static void sendBarMessage(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
    }

    public static void broadcastServerMessage(String message) {
        P.getInstance().getPluginMessage().sendGlobalMessage(message);
    }
}
