package fr.ChadOW.omegacore.shop.inventories;

import fr.ChadOW.api.managers.OmegaAPIUtils;
import fr.ChadOW.cinventory.CContent.CItem;
import fr.ChadOW.cinventory.interfaces.ItemCreator;
import fr.ChadOW.omegacore.shop.Shop;
import org.bukkit.Material;

import java.util.Arrays;

public class MenuInventory extends ShopInventory {
    private final Shop shop;
    private final CItem buyItem, sellItem, shopItem, quantityAvailable, bookSell1, bookSell10, bookSell32, bookSell64, bookBuy1, bookBuy10, bookBuy32, bookBuy64;

    public MenuInventory(Shop shop) {
        super(6*9, "§8§lMagasin de §6§l" + OmegaAPIUtils.tryToConvertIDToStringByUserAccount(shop.getOwner().toString()));
        this.shop = shop;

        shopItem = new CItem(new ItemCreator(shop.getItem())).setSlot(13);
        buyItem = new CItem(new ItemCreator(Material.GOLD_NUGGET,0)).setSlot(22);
        sellItem = new CItem(new ItemCreator(Material.IRON_NUGGET,0)).setSlot(31);
        quantityAvailable = new CItem(new ItemCreator(Material.CHEST,0)).setSlot(40);
        bookBuy1 = new CItem(new ItemCreator(Material.BOOK, 0).setName("§aAcheter 1x")).setSlot(19);
        bookBuy10 = new CItem(new ItemCreator(Material.BOOK, 0).setName("§aAcheter 10x").setAmount(10)).setSlot(20);
        bookBuy32 = new CItem(new ItemCreator(Material.BOOK, 0).setName("§aAcheter 32x").setAmount(32)).setSlot(24);
        bookBuy64 = new CItem(new ItemCreator(Material.BOOK, 0).setName("§aAcheter 64x").setAmount(64)).setSlot(25);
        bookSell1 = new CItem(new ItemCreator(Material.BOOK, 0).setName("§bVendre 1x")).setSlot(28);
        bookSell10 = new CItem(new ItemCreator(Material.BOOK, 0).setName("§bVendre 10x").setAmount(10)).setSlot(29);
        bookSell32 = new CItem(new ItemCreator(Material.BOOK, 0).setName("§bVendre 32x").setAmount(32)).setSlot(33);
        bookSell64 = new CItem(new ItemCreator(Material.BOOK, 0).setName("§bVendre 64x").setAmount(64)).setSlot(34);

        update();
        updateBuyLore();
        updateSellLore();
        addElement(quantityAvailable);
        addElement(buyItem);
        addElement(sellItem);
        addElement(shopItem);
    }

    public void update() {
        quantityAvailable.setName("§fQuantité disponible: §e" + shop.getAmount());
        buyItem.setName("§fPrix d'achat actuel: §e" + shop.getBuyPrice() + "$");
        sellItem.setName("§fPrix de vente actuel: §a" + shop.getSellPrice() + "$");

        if (shop.getAmount() > 0)
            quantityAvailable.setMaterial(Material.CHEST).setAmount(shop.getAmount());
        else
            quantityAvailable.setMaterial(Material.BARRIER).setAmount(1);
        if (shop.getBuyPrice() > 0) {
            buyItem.setMaterial(Material.GOLD_NUGGET).setAmount(shop.getBuyPrice());
            addElement(bookBuy1);
            addElement(bookBuy10);
            addElement(bookBuy32);
            addElement(bookBuy64);
        } else {
            buyItem.setMaterial(Material.BARRIER).setAmount(1);
            removeElement(bookBuy1);
            removeElement(bookBuy10);
            removeElement(bookBuy32);
            removeElement(bookBuy64);
        }
        if (shop.getSellPrice() > 0) {
            sellItem.setMaterial(Material.IRON_NUGGET).setAmount(shop.getSellPrice());
            addElement(bookSell1);
            addElement(bookSell10);
            addElement(bookSell32);
            addElement(bookSell64);
        } else {
            sellItem.setMaterial(Material.BARRIER).setAmount(1);
            removeElement(bookSell1);
            removeElement(bookSell10);
            removeElement(bookSell32);
            removeElement(bookSell64);
        }


        quantityAvailable.updateDisplay();
        buyItem.updateDisplay();
        sellItem.updateDisplay();
    }

    public void updateBuyLore() {
        int buyPrice = shop.getBuyPrice();
        bookBuy1.setDescription(Arrays.asList(
                "§f",
                "§fPrix: §e" + buyPrice + "$"
        ));
        bookBuy10.setDescription(Arrays.asList(
                "§f",
                "§fPrix: §e" + buyPrice *10 + "$"
        ));
        bookBuy32.setDescription(Arrays.asList(
                "§f",
                "§fPrix: §e" + buyPrice *32 + "$"
        ));
        bookBuy64.setDescription(Arrays.asList(
                "§f",
                "§fPrix: §e" + buyPrice *64 + "$"
        ));
    }

    public void updateSellLore() {
        int sellPrice = shop.getSellPrice();
        bookSell1.setDescription(Arrays.asList(
                "§f",
                "§fGains: §e" + sellPrice + "$"
        ));
        bookSell10.setDescription(Arrays.asList(
                "§f",
                "§fGains: §e" + sellPrice *10 + "$"
        ));
        bookSell32.setDescription(Arrays.asList(
                "§f",
                "§fGains: §e" + sellPrice *32 + "$"
        ));
        bookSell64.setDescription(Arrays.asList(
                "§f",
                "§fGains: §e" + sellPrice *64 + "$"
        ));
    }

    public CItem getBuyItem() {
        return buyItem;
    }

    public CItem getSellItem() {
        return sellItem;
    }

    public CItem getShopItem() {
        return shopItem;
    }

    public CItem getQuantityAvailable() {
        return quantityAvailable;
    }
}
