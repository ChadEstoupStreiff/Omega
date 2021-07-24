package fr.ChadOW.omegacore.shop;

import fr.ChadOW.api.enums.Rank;
import fr.ChadOW.cinventory.CContent.CInventory;
import fr.ChadOW.cinventory.CContent.CItem;
import fr.ChadOW.cinventory.ItemCreator;
import fr.ChadOW.omegacore.economie.Eco;
import org.bukkit.Material;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.Arrays;

public class ShopListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onItemDespawn(final ItemDespawnEvent event) {
        if (event.getEntity().getCustomName() == null) return;
        if (event.getEntity().getCustomName().equals("Shop display item"))
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onHopperPickup(final InventoryPickupItemEvent event) {
        if (event.getItem().getCustomName() == null) return;
        if (event.getItem().getCustomName().equals("Shop display item"))
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerFish(final PlayerFishEvent event) {
        if (event.getCaught() instanceof Item) {
            final Item item = (Item)event.getCaught();
            if (item.getCustomName() == null) return;
            if (item.getCustomName().equals("Shop display item"))
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract (PlayerInteractEntityEvent event){
        if (event.getRightClicked() instanceof Horse){
            Horse horse = (Horse) event.getRightClicked();
            if (horse.getCustomName() == null) return;

            if (horse.getCustomName().equals("Horse Shop"))
                ShopManager.shops.get(horse).openShop(event.getPlayer());
        }
    }
}
