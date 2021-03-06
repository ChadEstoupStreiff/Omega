package fr.ChadOW.omegacore.shop;

import fr.ChadOW.omegacore.P;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class ShopListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onItemDespawn(final ItemDespawnEvent event) {
        if (event.getEntity().getCustomName() == null) return;
        if (event.getEntity().getCustomName().equals("§6Shop display item"))
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onHopperPickup(final InventoryPickupItemEvent event) {
        if (event.getItem().getCustomName() == null) return;
        if (event.getItem().getCustomName().equals("§6Shop display item"))
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerFish(final PlayerFishEvent event) {
        if (event.getCaught() instanceof Item) {
            final Item item = (Item)event.getCaught();
            if (item.getCustomName() == null) return;
            if (item.getCustomName().equals("§6Shop display item"))
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract (PlayerInteractEntityEvent event){
        if (event.getRightClicked() instanceof Bee){
            Bee bee = (Bee) event.getRightClicked();
            if (bee.getCustomName() != null && bee.getCustomName().equals("§6Bee Shop")){
                Shop shop = P.getInstance().getShopManager().getShop(bee);
                if (shop != null)
                    shop.openShopMenu(event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onDamage (EntityDamageEvent event){
        if (event.getEntity() instanceof Bee){
            Bee bee = (Bee) event.getEntity();
            if (bee.getCustomName() != null && bee.getCustomName().equals("§6Bee Shop")){
                if (P.getInstance().getShopManager().getShop(bee) != null)
                    event.setCancelled(true);
            }
        }
    }
}
