package fr.ChadOW.omegacore.shop;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class SerializableShop {
    public Map<String, Object> itemStackSerialized;
    public final double x;
    public final double y;
    public final double z;
    public final String world;
    public final int buyPrice;
    public final int sellPrice;
    public final int quantity;
    public final String uuid;
    public final boolean adminShop;
   
    public SerializableShop(Shop shop) {
        itemStackSerialized = shop.getItem().serialize();
        adminShop = shop.isAdminShop();
        world = Objects.requireNonNull(shop.getLocation().getWorld()).getName();
        x = shop.getLocation().getX();
        y = shop.getLocation().getY();
        z = shop.getLocation().getZ();
        buyPrice = shop.getBuyPrice();
        sellPrice = shop.getSellPrice();
        quantity = shop.getAmount();
        uuid = shop.getOwner().toString();
    }

    public void createShop(ShopManager shopManager) {
        shopManager
                .createShop(new Location(Bukkit.getWorld(world), x, y, z),
                        ItemStack.deserialize(itemStackSerialized),
                        buyPrice, sellPrice, quantity, adminShop, UUID.fromString(uuid));
    }
}