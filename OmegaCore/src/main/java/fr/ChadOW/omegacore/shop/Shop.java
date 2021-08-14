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

    private boolean adminShop;
    private final Location location;
    private ItemStack item;
    private int buyPrice;
    private int sellPrice;
    private int amount;
    private final UUID owner;
    private Hologram hologram;
    private Item shopDisplayItem;
    private Bee bee;
    private CInventory menu;
    private CInventory configuration;
    private final CItem quantity_available = new CItem(new ItemCreator(Material.CHEST,0));
    private final CItem quantity_item = new CItem(new ItemCreator(Material.CHEST,0));

    Shop(Location location, ItemStack item, int buyPrice, int sellPrice, int amount,boolean adminShop, UUID owner) {
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
        menu = new CInventory(6*9, "§8§lMagasin de §6§l" + OmegaAPIUtils.tryToConvertIDToStringByUserAccount(owner.toString()));
        configuration = new CInventory(6*9, "§§8§lEdition du magasin");

        for (int i=0;i<53;i++){
            menu.addElement(new CItem(new ItemCreator(Material.WHITE_STAINED_GLASS_PANE, 0)
                    .setName("§f")).setSlot(i));
            configuration.addElement(new CItem(new ItemCreator(Material.WHITE_STAINED_GLASS_PANE, 0)
                    .setName("§f")).setSlot(i));
        }

        int[] orangePanesIndex = {1,2,3,5,6,7,9,17,36,44,46,47,48,50,51,52};
        int[] whitePanesIndex = {0,4,8,18,26,27,35,45,49,53};

        for (int i: orangePanesIndex){
            menu.addElement(new CItem(new ItemCreator(Material.ORANGE_STAINED_GLASS_PANE, 0)
                    .setName("§f")).setSlot(i));
            configuration.addElement(new CItem(new ItemCreator(Material.ORANGE_STAINED_GLASS_PANE, 0)
                    .setName("§f")).setSlot(i));
        }
        for (int i: whitePanesIndex){
            menu.addElement(new CItem(new ItemCreator(Material.WHITE_STAINED_GLASS_PANE, 0)
                    .setName("§f")).setSlot(i));
            configuration.addElement(new CItem(new ItemCreator(Material.WHITE_STAINED_GLASS_PANE, 0)
                    .setName("§f")).setSlot(i));
        }

        CItem buy_price = new CItem(new ItemCreator(Material.GOLD_NUGGET,0)
                .setName(String.format("Prix d'achat actuel: %s$",buyPrice))).setSlot(22);
        CItem sell_price = new CItem(new ItemCreator(Material.IRON_NUGGET,0)
                .setName(String.format(ChatColor.GREEN + "Prix de vente actuel: %s$",sellPrice))).setSlot(31);
        CItem shop_item = new CItem(new ItemCreator(item)).setSlot(13);

        menu.addElement(quantity_available
                .setName(String.format("Quantité disponible: %s%s",ChatColor.YELLOW,amount)).setSlot(13));

        configuration.addElement(shop_item
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

        configuration.addElement(new CItem(new ItemCreator(Material.ARROW,0)
                .setName("§eRevenir au shop")).setSlot(53)
                .addEvent((inventoryRepresentation, itemRepresentation, player, clickContext) -> openShopMenu(player)));

        configuration.addElement(new CItem(new ItemCreator(Material.WHITE_BANNER,0)
                .setName(ChatColor.YELLOW +"Diminuer le prix d'achat de 100$")).setSlot(19)
                .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> {
                    if (buyPrice - 100 >= 0) buyPrice -= 100;
                    buy_price.setName(String.format("Prix d'achat actuel: %s$",buyPrice));
                    buy_price.updateDisplay();
                    updateHologram();
                    updateMenus();
                }));
        configuration.addElement(new CItem(new ItemCreator(Material.WHITE_BANNER,0)
                .setName(ChatColor.YELLOW +"Diminuer le prix d'achat de 10$")).setSlot(20)
                .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> {
                    if (buyPrice - 10 >= 0) buyPrice -= 10;
                    buy_price.setName(String.format("Prix d'achat actuel: %s$",buyPrice));
                    buy_price.updateDisplay();
                    updateHologram();
                    updateMenus();
                }));
        configuration.addElement(new CItem(new ItemCreator(Material.WHITE_BANNER,0)
                .setName(ChatColor.YELLOW +"Diminuer le prix d'achat de 1$")).setSlot(21)
                .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> {
                    if (buyPrice - 1 >= 0) buyPrice -= 1;
                    buy_price.setName(String.format("Prix d'achat actuel: %s$",buyPrice));
                    buy_price.updateDisplay();
                    updateHologram();
                    updateMenus();
                }));
        configuration.addElement(buy_price);
        configuration.addElement(new CItem(new ItemCreator(Material.WHITE_BANNER,0)
                .setName(ChatColor.YELLOW +"Augmenter le prix d'achat de 1$")).setSlot(23)
                .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> {
                    buyPrice += 1;
                    buy_price.setName(String.format("Prix d'achat actuel: %s$",buyPrice));
                    buy_price.updateDisplay();
                    updateHologram();
                    updateMenus();
                }));
        configuration.addElement(new CItem(new ItemCreator(Material.WHITE_BANNER,0)
                .setName(ChatColor.YELLOW +"Augmenter le prix d'achat de 10$")).setSlot(24)
                .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> {
                    buyPrice += 10;
                    buy_price.setName(String.format("Prix d'achat actuel: %s$",buyPrice));
                    buy_price.updateDisplay();
                    updateHologram();
                    updateMenus();
                }));
        configuration.addElement(new CItem(new ItemCreator(Material.WHITE_BANNER,0)
                .setName(ChatColor.YELLOW +"Augmenter le prix d'achat de 100$")).setSlot(25)
                .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> {
                    buyPrice += 100;
                    buy_price.setName(String.format("Prix d'achat actuel: %s$",buyPrice));
                    buy_price.updateDisplay();
                    updateHologram();
                    updateMenus();
                }));

        configuration.addElement(new CItem(new ItemCreator(Material.WHITE_BANNER,0)
                .setName(ChatColor.GREEN + "Diminuer le prix de vente de 100$")).setSlot(28)
                .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> {
                    if (sellPrice - 100 >= 0) sellPrice -= 100;
                    sell_price.setName(String.format(ChatColor.GREEN + "Prix de vente actuel: %s$",sellPrice));
                    sell_price.updateDisplay();
                    updateHologram();
                    updateMenus();
                }));
        configuration.addElement(new CItem(new ItemCreator(Material.WHITE_BANNER,0)
                .setName(ChatColor.GREEN + "Diminuer le prix de vente de 10$")).setSlot(29)
                .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> {
                    if (sellPrice - 10 >= 0) sellPrice -= 10;
                    sell_price.setName(String.format(ChatColor.GREEN + "Prix de vente actuel: %s$",sellPrice));
                    sell_price.updateDisplay();
                    updateHologram();
                    updateMenus();
                }));
        configuration.addElement(new CItem(new ItemCreator(Material.WHITE_BANNER,0)
                .setName(ChatColor.GREEN + "Diminuer le prix de vente de 1$")).setSlot(30)
                .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> {
                    if (sellPrice - 1 >= 0) sellPrice -= 1;
                    sell_price.setName(String.format(ChatColor.GREEN + "Prix de vente actuel: %s$",sellPrice));
                    sell_price.updateDisplay();
                    updateHologram();
                    updateMenus();
                }));
        configuration.addElement(sell_price);
        configuration.addElement(new CItem(new ItemCreator(Material.WHITE_BANNER,0)
                .setName(ChatColor.GREEN + "Augmenter le prix de vente de 1$")).setSlot(32)
                .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> {
                    sellPrice += 1;
                    sell_price.setName(String.format(ChatColor.GREEN + "Prix de vente actuel: %s$",sellPrice));
                    sell_price.updateDisplay();
                    updateHologram();
                    updateMenus();
                }));
        configuration.addElement(new CItem(new ItemCreator(Material.WHITE_BANNER,0)
                .setName(ChatColor.GREEN + "Augmenter le prix de vente de 10$")).setSlot(33)
                .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> {
                    sellPrice += 10;
                    sell_price.setName(String.format(ChatColor.GREEN + "Prix de vente actuel: %s$",sellPrice));
                    sell_price.updateDisplay();
                    updateHologram();
                    updateMenus();
                }));
        configuration.addElement(new CItem(new ItemCreator(Material.WHITE_BANNER,0)
                .setName(ChatColor.GREEN + "Augmenter le prix de vente de 100$")).setSlot(34)
                .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> {
                    sellPrice += 100;
                    sell_price.setName(String.format(ChatColor.GREEN + "Prix de vente actuel: %s$",sellPrice));
                    sell_price.updateDisplay();
                    updateHologram();
                    updateMenus();
                }));

        configuration.addElement(quantity_item.setName(String.format("Quantité: %s",amount))
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
                .setSlot(40));
        updateMenus();
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

    private void updateHologram() {
        hologram.setLineAtIndex("§6Magasin de " + item.getType().toString().toLowerCase(), 0);
        if (adminShop) hologram.setLineAtIndex("§fOmegaShop",1);
        else hologram.setLineAtIndex("§fStock: §6" + amount,1);
        if (buyPrice == 0) hologram.removeLine(2);
        else hologram.setLineAtIndex("§fAchat: §e" + buyPrice + "$",2);
        if (sellPrice == 0) hologram.removeLine(3);
        else hologram.setLineAtIndex("§fVente: §b" + sellPrice + "$",3);
        if (sellPrice == 0 && buyPrice == 0) hologram.removeLine(2);
    }

    public void openShop(Player player) {
        if (UserAccount.getAccount(player.getUniqueId()).getRank().getStaffPower() >= Rank.DEV.getStaffPower()) {
            CInventory clone = menu;
            if (adminShop) {
                clone.addElement(new CItem(new ItemCreator(Material.GREEN_WOOL, 0)
                        .setName(ChatColor.RED + "Désactiver l'admin shop")).setSlot(45)
                        .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) ->{
                            adminShop = false;
                            updateHologram();
                            updateMenus();
                        }));
            }
            else {
                clone.addElement(new CItem(new ItemCreator(Material.RED_WOOL, 0)
                        .setName(ChatColor.GREEN + "Activer l'admin shop")).setSlot(45)
                        .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> {
                            adminShop = true;
                            updateHologram();
                            updateMenus();
                        }));
            }
            clone.open(player);
        }
        else if (owner.equals(player.getUniqueId()))
            configuration.open(player);
        else
            openShopMenu(player);
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

    public void updateMenus() {
        if (buyPrice != 0){
            menu.addElement(new CItem(new ItemCreator(Material.GOLD_NUGGET,0)
                    .setName(ChatColor.YELLOW + "Acheter x1")).setSlot(20)
                    .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> buyItem(1, p)));
            menu.addElement(new CItem(new ItemCreator(Material.GOLD_INGOT,0)
                    .setName(ChatColor.YELLOW + "Acheter x16")).setSlot(22)
                    .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> buyItem(-16, p)));
            menu.addElement(new CItem(new ItemCreator(Material.GOLD_BLOCK,0)
                    .setName(ChatColor.YELLOW + "Acheter x64")).setSlot(24)
                    .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> buyItem(-64, p)));
        }
        else {
            for (int i=20;i<=24;i+=2) {
                menu.removeElement(menu.getElement(i));
                menu.addElement(new CItem(new ItemCreator(Material.WHITE_STAINED_GLASS_PANE, 0)
                        .setName("§f")).setSlot(i));
            }
        }
        if (sellPrice != 0){
            menu.addElement(new CItem(new ItemCreator(Material.IRON_NUGGET,0)
                    .setName(ChatColor.GREEN + "Vendre x1")).setSlot(29)
                    .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> sellItem(1, p)));
            menu.addElement(new CItem(new ItemCreator(Material.IRON_INGOT,0)
                    .setName(ChatColor.GREEN + "Vendre x16")).setSlot(31)
                    .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> sellItem(16, p)));
            menu.addElement(new CItem(new ItemCreator(Material.IRON_BLOCK,0)
                    .setName(ChatColor.GREEN + "Vendre x64")).setSlot(33)
                    .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> sellItem(64, p)));
        }
        else {
            for (int i=29;i<=33;i+=2) {
                menu.removeElement(menu.getElement(i));
                menu.addElement(new CItem(new ItemCreator(Material.WHITE_STAINED_GLASS_PANE, 0)
                        .setName("§f")).setSlot(i));
            }
        }
        if (adminShop){
            quantity_available.setName(Objects.requireNonNull(item.getItemMeta()).getDisplayName());
            menu.removeElement(menu.getElement(40));
            menu.addElement(new CItem(new ItemCreator(Material.WHITE_STAINED_GLASS_PANE, 0)
                    .setName("§f")).setSlot(40));
            menu.setName("§8§lOmegaShop");
        }
        else {
            quantity_item.setName(String.format("Quantité: %s", amount));
            quantity_item.updateDisplay();
            quantity_available.setName(String.format("Quantité disponible: %s%s", ChatColor.YELLOW, amount));
            menu.setName("§8§lMagasin de §6§l" + OmegaAPIUtils.tryToConvertIDToStringByUserAccount(owner.toString()));
            menu.addElement(new CItem(new ItemCreator(Material.PLAYER_HEAD,0)
                    .setOwner(Objects.requireNonNull(OmegaAPIUtils.tryToConvertIDToStringByUserAccount(owner.toString()))))
                    .setSlot(40));
        }
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

    private void openShopMenu (Player player){
        if (!(owner.equals(player.getUniqueId()) ||
                UserAccount.getAccount(player.getUniqueId()).getRank().getStaffPower() >= Rank.DEV.getStaffPower()))
            menu.open(player);
        CInventory clone = menu;
        clone.addElement(new CItem(new ItemCreator(Material.REPEATER,0)
                .setName("§eOuvrir le menu d'édition de shop")).setSlot(53)
                .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> configuration.open(p)));
        clone.open(player);
    }

    public boolean isAdminShop() {
        return adminShop;
    }

    private boolean canStockMore(int quantity){
        if (adminShop) return true;
        int power = UserAccount.getAccount(owner).getRank().getPower();
        int maxStock = (power+1)*9 * item.getMaxStackSize();
        return amount + quantity <= maxStock;
    }
}

