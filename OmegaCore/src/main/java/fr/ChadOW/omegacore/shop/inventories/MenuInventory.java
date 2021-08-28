package fr.ChadOW.omegacore.shop.inventories;

import fr.ChadOW.api.accounts.BankAccount;
import fr.ChadOW.api.accounts.UserAccount;
import fr.ChadOW.api.managers.OmegaAPIUtils;
import fr.ChadOW.cinventory.CContent.CItem;
import fr.ChadOW.cinventory.interfaces.ItemCreator;
import fr.ChadOW.omegacore.shop.Shop;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class MenuInventory extends ShopInventory {
    public static final String prefix = "§6[§eShop§6] §r";

    private final Shop shop;
    private final CItem buyItem, sellItem, quantityAvailable, bookSell1, bookSell10, bookSell32, bookSell64, bookBuy1, bookBuy10, bookBuy32, bookBuy64;

    public MenuInventory(Shop shop) {
        super(6*9, "§8§lMagasin de §6§l" + OmegaAPIUtils.tryToConvertIDToStringByUserAccount(shop.getOwner().toString()));
        this.shop = shop;

        addElement(new CItem(new ItemCreator(shop.getItem())).setSlot(13));
        buyItem = new CItem(new ItemCreator(Material.GOLD_NUGGET,0)).setSlot(22);
        sellItem = new CItem(new ItemCreator(Material.IRON_NUGGET,0)).setSlot(31);
        quantityAvailable = new CItem(new ItemCreator(Material.CHEST,0)).setSlot(40);
        bookBuy1 = new CItem(new ItemCreator(Material.BOOK, 0).setName("§aAcheter 1x")).addEvent((inventory, item, player, clickContext) -> tryBuy(player, 1)).setSlot(19);
        bookBuy10 = new CItem(new ItemCreator(Material.BOOK, 0).setName("§aAcheter 10x").setAmount(10)).addEvent((inventory, item, player, clickContext) -> tryBuy(player, 10)).setSlot(20);
        bookBuy32 = new CItem(new ItemCreator(Material.BOOK, 0).setName("§aAcheter 32x").setAmount(32)).addEvent((inventory, item, player, clickContext) -> tryBuy(player, 32)).setSlot(24);
        bookBuy64 = new CItem(new ItemCreator(Material.BOOK, 0).setName("§aAcheter 64x").setAmount(64)).addEvent((inventory, item, player, clickContext) -> tryBuy(player, 64)).setSlot(25);
        bookSell1 = new CItem(new ItemCreator(Material.BOOK, 0).setName("§bVendre 1x")).addEvent((inventory, item, player, clickContext) -> trySell(player, 1)).setSlot(28);
        bookSell10 = new CItem(new ItemCreator(Material.BOOK, 0).setName("§bVendre 10x").setAmount(10)).addEvent((inventory, item, player, clickContext) -> trySell(player, 10)).setSlot(29);
        bookSell32 = new CItem(new ItemCreator(Material.BOOK, 0).setName("§bVendre 32x").setAmount(32)).addEvent((inventory, item, player, clickContext) -> trySell(player, 32)).setSlot(33);
        bookSell64 = new CItem(new ItemCreator(Material.BOOK, 0).setName("§bVendre 64x").setAmount(64)).addEvent((inventory, item, player, clickContext) -> trySell(player, 64)).setSlot(34);

        update();
        updateBuyLore();
        updateSellLore();
        addElement(quantityAvailable);
        addElement(buyItem);
        addElement(sellItem);
    }

    private void tryBuy(Player player, int n) {
        if (shop.getAmount() >= n) {
            BankAccount buyerBank = UserAccount.getAccount(player.getUniqueId()).getBankAccount();
            if (buyerBank.getAmount() >= shop.getBuyPrice() * n) {

                BankAccount shopBank = UserAccount.getAccount(shop.getOwner()).getBankAccount();
                for (int i = 0; i < n; i++)
                    player.getLocation().getWorld().dropItem(player.getLocation(), shop.getItem());
                shop.setAmount(shop.getAmount() -n);
                buyerBank.payAccount(n * shop.getBuyPrice(), shopBank);

                player.sendMessage(prefix + "§a" + n + "x " + shop.getItemName() + " §r acheté pour §e" + n * shop.getBuyPrice() + "$");
            } else {
                player.sendMessage(prefix + "§cVous n'avez pas la somme nécessaire !");
            }
        } else {
            player.sendMessage(prefix + "§cLe stock du shop est insuffisant !");
        }
    }

    private void trySell(Player player, int n) {
        if (player.getInventory().containsAtLeast(shop.getItem(), n)) {
            BankAccount shopBank = UserAccount.getAccount(shop.getOwner()).getBankAccount();
            if (shopBank.getAmount() >= shop.getSellPrice() * n) {

                BankAccount sellerBank = UserAccount.getAccount(player.getUniqueId()).getBankAccount();
                for (int i = 0; i < n; i++)
                    player.getInventory().remove(shop.getItem());
                shop.setAmount(shop.getAmount() +n);
                shopBank.payAccount(n * shop.getSellPrice(), sellerBank);

                player.sendMessage(prefix + "§a" + n + "x " + shop.getItemName() + " §r vendu pour §b" + n * shop.getSellPrice() + "$");
            } else {
                player.sendMessage(prefix + "§cLe vendeur n'a plus d'argent !");
            }
        } else {
            player.sendMessage(prefix + "§cVous n'avez pas la quantité nécessaire !");
        }
    }

    public void update() {
        quantityAvailable.setName("§fQuantité disponible: §e" + shop.getAmount());
        quantityAvailable.setDescription(Arrays.asList(
                "§f",
                "§fQuantité maximum: §e" + shop.getMaxAmount()
        ));
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
        addElement(new CItem(new ItemCreator(shop.getItem())).setSlot(13));


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

    public CItem getQuantityAvailable() {
        return quantityAvailable;
    }
}
