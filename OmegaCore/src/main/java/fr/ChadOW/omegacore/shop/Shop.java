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
        spawnDisplayItem();
        spawnBee();
        spawnHolograms();
    }

    private void initInventories() {
        menuInventory = new MenuInventory(this);
        configurationInventory = new ConfigurationInventory(this);
        adminInventory = new AdminInventory(this);

        /*configurationInventory.addElement(shop_item
                .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> {
                    if (clickContext.getInventoryAction().equals(Action.SWAP_WITH_CURSOR))
                        if (amount == 0) {
                            item = new ItemCreator(p.getItemOnCursor()).setAmount(1).getItem();
                            shop_item.setItemCreator(new ItemCreator(p.getItemOnCursor()).setAmount(1));
                            shop_item.updateDisplay();
                            updateMenus();
                            updateHologram();
                        }
                }));

        configurationInventory.addElement(quantity_available.setName(String.format("Quantité: %s",amount))
                .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> {
                    if (clickContext.getInventoryAction().equals(Action.SWAP_WITH_CURSOR)) {
                        if (p.getItemOnCursor().isSimilar(item)) {
                            if (canStockMore(p.getItemOnCursor().getAmount())) {
                                amount += p.getItemOnCursor().getAmount();
                                p.setItemOnCursor(new ItemStack(Material.AIR));
                            }
                        }
                    }
                    else if (clickContext.getClickType().equals(ClickType.LEFT)) {
                        if (p.getInventory().containsAtLeast(item, item.getMaxStackSize())) {
                            if (canStockMore(item.getMaxStackSize())) {
                                p.getInventory().removeItem(new ItemCreator(item).setAmount(item.getMaxStackSize()).getItem());
                                amount += item.getMaxStackSize();
                            }
                        }
                    }
                    else if (clickContext.getClickType().equals(ClickType.SHIFT_LEFT)) {
                        for (ItemStack itemStack : p.getInventory().getStorageContents()){
                            if (itemStack != null)
                                if (itemStack.isSimilar(item)) {
                                    if (canStockMore(itemStack.getAmount())) {
                                        amount += itemStack.getAmount();
                                        p.getInventory().removeItem(itemStack);
                                    }
                                }
                        }
                    }
                    else if (clickContext.getClickType().equals(ClickType.SHIFT_RIGHT)){
                        for (ItemStack itemStack : p.getInventory().getStorageContents()) {
                            if (itemStack == null) {
                                if (amount - item.getMaxStackSize() >= 0) {
                                    amount -= item.getMaxStackSize();
                                    p.getInventory().addItem(new ItemCreator(item).setAmount(item.getMaxStackSize()).getItem());
                                } else {
                                    p.getInventory().addItem(new ItemCreator(item).setAmount(amount).getItem());
                                    amount = 0;
                                }
                            }
                            else if (itemStack.isSimilar(item)) {
                                if (amount - item.getMaxStackSize() - itemStack.getAmount() >= 0) {
                                    amount -= item.getMaxStackSize() - itemStack.getAmount();
                                    p.getInventory().addItem(new ItemCreator(item).setAmount(item.getMaxStackSize() + 1 - itemStack.getAmount()).getItem());
                                }
                            }
                        }
                    }
                    updateHologram();
                    updateMenus();
                })
                .setSlot(40));*/
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
        updateHologram();
    }

    private void sellItem(int quantity, Player player) {
        if (player.getInventory().containsAtLeast(item,quantity)){
            for (int i = 0; i < quantity; i++) {
                player.getInventory().removeItem(item);
            }
            if (canStockMore(quantity)) {
                amount += quantity;
                updateMenus();
                updateHologram();
                //pay the player and withdraw the owner
                UserAccount sellerAccount = UserAccount.getAccount(player.getUniqueId());
                sellerAccount.getBankAccount().addAmount(sellPrice * quantity);
                UserAccount shopperAccount = UserAccount.getAccount(owner);
                shopperAccount.getBankAccount().removeAmount(sellPrice * quantity);
            }
        }
    }

    private void buyItem(int quantity, Player player){
        if (quantity < amount) return;
        int available = 0;
        for (ItemStack itemStack : player.getInventory().getStorageContents()){
            if (itemStack.getType().equals(Material.AIR)) available += item.getMaxStackSize();
            else if (itemStack.isSimilar(item)) available += item.getMaxStackSize() - itemStack.getAmount();
        }
        if (available >= quantity){
            for (int i = 0; i < quantity; i++) {
                player.getInventory().addItem(item);
            }
            amount -= quantity;
            updateMenus();
            updateHologram();
            //pay the owner and withdraw the player
            UserAccount buyerAccount = UserAccount.getAccount(player.getUniqueId());
            buyerAccount.getBankAccount().removeAmount(buyPrice * quantity);
            UserAccount shopperAccount = UserAccount.getAccount(owner);
            shopperAccount.getBankAccount().addAmount(buyPrice * quantity);
        }
    }

    public Location getLocation() {
        return location;
    }

    public Bee getBee() {
        return bee;
    }

    private void updateHologram() {
        hologram.clear();
        if (adminShop)
            hologram.addLine("§6OmegaShop");
        else {
            hologram.addLine("§6Magasin de " + item.getType().toString().toLowerCase());
            hologram.addLine("§fQuantité: §6" + amount);
        }
        if (buyPrice > 0)
            hologram.addLine("§fAchat: §e" + buyPrice + "$");
        if (sellPrice > 0)
            hologram.addLine("§fVente: §b" + sellPrice + "$");
        shopDisplayItem.setItemStack(new ItemStack(item.getType()));
    }

    public void updateMenus() {
        getMenuInventory().update();
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
            updateMenus();
            getMenuInventory().updateBuyLore();
            updateHologram();
        }
    }

    public void setSellPrice(int sellPrice) {
        if (sellPrice >= 0 || this.sellPrice != 0) {
            this.sellPrice = Math.max(sellPrice, 0);
            updateMenus();
            getMenuInventory().updateSellLore();
            updateHologram();
        }
    }

    public void setAmount(int amount) {
        this.amount = amount;
        updateMenus();
    }

    public void setItem(ItemStack item) {
        this.item = item;
        updateMenus();
        updateHologram();
    }

    public void setAdminShop(boolean adminShop) {
        this.adminShop = adminShop;
        updateMenus();
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
        int power = UserAccount.getAccount(owner).getRank().getPower();
        int maxStock;
        if (power >= Rank.MYTH.getPower()) maxStock = 36 * item.getMaxStackSize();
        else if (power >= Rank.LEGEND.getPower()) maxStock = 27 * item.getMaxStackSize();
        else if (power >= Rank.OLD.getPower()) maxStock = 18 * item.getMaxStackSize();
        else maxStock = 9 * item.getMaxStackSize();
        return amount + quantity <= maxStock;
    }
}

