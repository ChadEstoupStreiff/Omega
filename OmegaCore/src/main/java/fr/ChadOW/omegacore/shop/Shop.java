package fr.ChadOW.omegacore.shop;

import fr.ChadOW.api.accounts.UserAccount;
import fr.ChadOW.api.enums.Rank;
import fr.ChadOW.api.managers.OmegaAPIUtils;
import fr.ChadOW.cinventory.CContent.CInventory;
import fr.ChadOW.cinventory.CContent.CItem;
import fr.ChadOW.cinventory.interfaces.ItemCreator;
import fr.ChadOW.cinventory.interfaces.events.clickContent.Action;
import fr.ChadOW.cinventory.interfaces.events.clickContent.ClickType;
import fr.ChadOW.omegacore.P;
import fr.ChadOW.omegacore.economie.Eco;
import fr.ChadOW.omegacore.shop.inventories.AdminInventory;
import fr.ChadOW.omegacore.shop.inventories.ConfigurationInventory;
import fr.ChadOW.omegacore.shop.inventories.MenuInventory;
import fr.ChadOW.omegacore.utils.hologram.Hologram;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Bee;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Objects;
import java.util.UUID;

public class Shop {

    private final Location location;
    private final UUID owner;
    private int buyPrice;
    private int sellPrice;
    private int amount;
    private ItemStack item;
    private boolean adminShop;

    private Hologram hologram;
    private Item shopDisplayItem;
    private Bee bee;

    private MenuInventory menuInventory;
    private ConfigurationInventory configurationInventory;
    private AdminInventory adminInventory;

    Shop(Location location, ItemStack item, int buyPrice, int sellPrice, int amount, boolean adminShop, UUID owner) {
        this.location = location;
        this.item = item;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.amount = amount;
        this.owner = owner;
        this.adminShop = adminShop;
        initInventories();
        spawnBee();
        spawnHolograms();
    }

    private void initInventories() {
        menuInventory = new MenuInventory(this);
        configurationInventory = new ConfigurationInventory(this);
        adminInventory = new AdminInventory(this);
    }

    private void spawnDisplayItem() {
        ItemStack i = this.item.clone();
        shopDisplayItem = Objects.requireNonNull(location.getWorld()).dropItem(location, i);
        shopDisplayItem.setInvulnerable(true);
        shopDisplayItem.setVelocity(new Vector(0, 0, 0));
        shopDisplayItem.setGravity(false);
        shopDisplayItem.setPickupDelay(Integer.MAX_VALUE);
        shopDisplayItem.setOwner(owner);
        shopDisplayItem.setCustomName("§6Shop display item");
        shopDisplayItem.setCustomNameVisible(false);
        shopDisplayItem.setPersistent(true);
    }

    private void spawnBee() {
        bee = (Bee) Objects.requireNonNull(location.getWorld()).spawnEntity(location, EntityType.BEE);
        bee.setInvisible(true);
        bee.setInvulnerable(true);
        bee.setPersistent(true);
        bee.setCollidable(false);
        bee.setCustomName("§6Bee Shop");
        bee.setCustomNameVisible(false);
        bee.setAI(false);
        bee.setAdult();
        bee.setSilent(true);
    }

    private void spawnHolograms() {
        hologram = P.getInstance().getHologramManager().createHologram("Shop", location);
        spawnDisplayItem();
        updateHologram();
    }

    public Location getLocation() {
        return location;
    }

    public Bee getBee() {
        return bee;
    }

    private void updateHologram() {
        hologram.clear();
        hologram.addLine("§6Magasin de §r" + getItemName());
        if (!adminShop)
            hologram.addLine("§fQuantité: §6" + amount);
        if (buyPrice > 0)
            hologram.addLine("§fAchat: §e" + buyPrice + Eco.devise);
        if (sellPrice > 0)
            hologram.addLine("§fVente: §b" + sellPrice + Eco.devise);
        shopDisplayItem.setItemStack(new ItemStack(item));
    }

    void delete() {
        bee.remove();
        shopDisplayItem.remove();
        P.getInstance().getHologramManager().deleteHologram(hologram);
    }

    public ItemStack getItem() {
        return item;
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public int getAmount() {
        return amount;
    }

    public UUID getOwner() {
        return owner;
    }

    public MenuInventory getMenuInventory() {
        return menuInventory;
    }

    public ConfigurationInventory getConfigurationInventory() {
        return configurationInventory;
    }

    public AdminInventory getAdminInventory() {
        return adminInventory;
    }

    public void setBuyPrice(int buyPrice) {
        if (buyPrice >= 0 || this.buyPrice != 0) {
            this.buyPrice = Math.max(buyPrice, 0);
            getMenuInventory().update();
            getMenuInventory().updateBuyLore();
            updateHologram();
        }
    }

    public void setSellPrice(int sellPrice) {
        if (sellPrice >= 0 || this.sellPrice != 0) {
            this.sellPrice = Math.max(sellPrice, 0);
            getMenuInventory().update();
            getMenuInventory().updateSellLore();
            updateHologram();
        }
    }

    public void setAmount(int amount) {
        this.amount = amount;
        getMenuInventory().update();
        updateHologram();
    }

    public void setItem(ItemStack item) {
        this.item = item;
        getMenuInventory().update();
        getConfigurationInventory().update();
        updateHologram();
    }

    public void setAdminShop(boolean adminShop) {
        this.adminShop = adminShop;
        getMenuInventory().update();
        updateHologram();
    }

    public void openShopMenu(Player player){
        if (UserAccount.getAccount(player.getUniqueId()).getRank().getStaffPower() >= Rank.DEV.getStaffPower())
            adminInventory.open(player);
        else if (owner.equals(player.getUniqueId()))
            configurationInventory.open(player);
        else
            menuInventory.open(player);
    }

    public boolean isAdminShop() {
        return adminShop;
    }

    private boolean canStockMore(int quantity){
        if (adminShop) return true;
        return amount + quantity <= getMaxAmount();
    }

    public int getMaxAmount() {
        int power = UserAccount.getAccount(owner).getRank().getPower();
        if (power >= Rank.MYTH.getPower()) return 36 * item.getMaxStackSize();
        else if (power >= Rank.LEGEND.getPower()) return 27 * item.getMaxStackSize();
        else if (power >= Rank.OLD.getPower()) return 18 * item.getMaxStackSize();
        else return 9 * item.getMaxStackSize();
    }

    public String getItemName() {
        return (item.getItemMeta() != null && item.getItemMeta().getDisplayName().length() > 0) ? item.getItemMeta().getDisplayName() : item.getType().toString().toLowerCase();
    }
}

