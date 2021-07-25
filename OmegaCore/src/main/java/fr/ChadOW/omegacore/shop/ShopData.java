package fr.ChadOW.omegacore.shop;

import fr.ChadOW.api.managers.JedisManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.UUID;

public class ShopData {

    private final double x;
    private final double y;
    private final double z;
    private final String world;
    private final int buyPrice;
    private final int sellPrice;
    private final int amount;
    private final String material;
    private final String uuid;


    public ShopData(Shop shop) {
        world = Objects.requireNonNull(shop.getLocation().getWorld()).getName();
        x = shop.getLocation().getX();
        y = shop.getLocation().getY();
        z = shop.getLocation().getZ();
        buyPrice = shop.getBuyPrice();
        sellPrice = shop.getSellPrice();
        material = shop.getItem().getType().name();
        amount = shop.getAmount();
        uuid = shop.getOwner().toString();
    }

    public Shop createShop(ShopManager shopManager) {
        return shopManager
                .createShop(new Location(Bukkit.getWorld(world), x, y, z), new ItemStack(Material.getMaterial(material)),
                        buyPrice, sellPrice,amount,UUID.fromString(uuid));
    }

    @Override
    public String toString() {
        return JedisManager.getGson().toJson(this);
    }
}
