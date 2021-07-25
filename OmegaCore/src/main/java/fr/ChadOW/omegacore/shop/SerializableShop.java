package fr.ChadOW.omegacore.shop;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.io.Serializable;
import java.util.*;

@SuppressWarnings("serial")
public class SerializableShop implements Serializable {
    public int amount;
    public byte data;
    public short durability;
    public String material;
    public String displayName;
    public Map<Enchantment, Integer> enchantmentList;
    public List<String> lore;
    public final double x;
    public final double y;
    public final double z;
    public final String world;
    public final int buyPrice;
    public final int sellPrice;
    public final int quantity;
    public final String uuid;
   
    public SerializableShop(Shop shop) {
        ItemStack itemStack = shop.getItem();
        amount = itemStack.getAmount();
        durability = itemStack.getDurability();
        enchantmentList = itemStack.getEnchantments();
        material = itemStack.getType().name();
        data = itemStack.getData().getData();
        displayName = itemStack.getItemMeta().getDisplayName();
        lore = itemStack.getItemMeta().getLore();
        world = Objects.requireNonNull(shop.getLocation().getWorld()).getName();
        x = shop.getLocation().getX();
        y = shop.getLocation().getY();
        z = shop.getLocation().getZ();
        buyPrice = shop.getBuyPrice();
        sellPrice = shop.getSellPrice();
        material = shop.getItem().getType().name();
        quantity = shop.getAmount();
        uuid = shop.getOwner().toString();
    }

    public ItemStack getItemStack() {
        ItemStack newStack = new ItemStack(Objects.requireNonNull(Material.getMaterial(material)), amount);
        newStack.setData(new MaterialData(Material.getMaterial(material), data));
        newStack.setDurability(durability);
        newStack.addEnchantments(enchantmentList);
        ItemMeta newMeta = newStack.getItemMeta();
        newMeta.setLore(lore);
        newMeta.setDisplayName(displayName);
        newStack.setItemMeta(newMeta);
        return newStack;
    }

    public Shop createShop(ShopManager shopManager) {
        return shopManager
                .createShop(new Location(Bukkit.getWorld(world), x, y, z), getItemStack(),
                        buyPrice, sellPrice,quantity, UUID.fromString(uuid));
    }
}