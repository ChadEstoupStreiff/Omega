package fr.ChadOW.omegacore.shop;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.UUID;

public class SerializableShop {
    public ItemStack itemStack;
    public final double x;
    public final double y;
    public final double z;
    public final String world;
    public final int buyPrice;
    public final int sellPrice;
    public final int quantity;
    public final String uuid;
   
    public SerializableShop(Shop shop) {
        itemStack = shop.getItem();
        world = Objects.requireNonNull(shop.getLocation().getWorld()).getName();
        x = shop.getLocation().getX();
        y = shop.getLocation().getY();
        z = shop.getLocation().getZ();
        buyPrice = shop.getBuyPrice();
        sellPrice = shop.getSellPrice();
        quantity = shop.getAmount();
        uuid = shop.getOwner().toString();
    }

    public Shop createShop(ShopManager shopManager) {
        return shopManager
                .createShop(new Location(Bukkit.getWorld(world), x, y, z), itemStack,
                        buyPrice, sellPrice,quantity, UUID.fromString(uuid));
    }
}