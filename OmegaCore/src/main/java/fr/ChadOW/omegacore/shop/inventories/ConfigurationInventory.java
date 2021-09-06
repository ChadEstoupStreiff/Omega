package fr.ChadOW.omegacore.shop.inventories;

import fr.ChadOW.cinventory.CContent.CItem;
import fr.ChadOW.cinventory.interfaces.ItemCreator;
import fr.ChadOW.cinventory.interfaces.events.clickContent.ClickType;
import fr.ChadOW.omegacore.economie.Eco;
import fr.ChadOW.omegacore.shop.Shop;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigurationInventory extends ShopInventory {
    private final Shop shop;

    public ConfigurationInventory(Shop shop) {
        super(6*9, "§§8§lEdition du magasin");
        this.shop = shop;

        addElement(shop.getMenuInventory().getBuyItem());
        addElement(shop.getMenuInventory().getSellItem());
        addElement(shop.getMenuInventory().getQuantityAvailable());

        addElement(new CItem(new ItemCreator(Material.WHITE_BANNER,0)//.addBannerPreset(ItemCreator.BannerPreset.moin, DyeColor.BLACK)
                .setName(ChatColor.YELLOW +"§cDiminuer§f le prix d'achat de §e100" + Eco.devise)).setSlot(19)
                .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> shop.setBuyPrice(shop.getBuyPrice() - 100)));
        addElement(new CItem(new ItemCreator(Material.WHITE_BANNER,0)//.addBannerPreset(ItemCreator.BannerPreset.moin, DyeColor.BLACK)
                .setName(ChatColor.YELLOW +"§cDiminuer§f le prix d'achat de §e10" + Eco.devise)).setSlot(20)
                .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> shop.setBuyPrice(shop.getBuyPrice() - 10)));
        addElement(new CItem(new ItemCreator(Material.WHITE_BANNER,0)//.addBannerPreset(ItemCreator.BannerPreset.moin, DyeColor.BLACK)
                .setName(ChatColor.YELLOW +"§cDiminuer§f le prix d'achat de §e1" + Eco.devise)).setSlot(21)
                .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> shop.setBuyPrice(shop.getBuyPrice() - 1)));
        addElement(new CItem(new ItemCreator(Material.WHITE_BANNER,0)//.addBannerPreset(ItemCreator.BannerPreset.plus, DyeColor.BLACK)
                .setName(ChatColor.YELLOW +"§aAugmenter§f le prix d'achat de §e1" + Eco.devise)).setSlot(23)
                .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> shop.setBuyPrice(shop.getBuyPrice() + 1)));
        addElement(new CItem(new ItemCreator(Material.WHITE_BANNER,0)//.addBannerPreset(ItemCreator.BannerPreset.plus, DyeColor.BLACK)
                .setName(ChatColor.YELLOW +"§aAugmenter§f le prix d'achat de §e10" + Eco.devise)).setSlot(24)
                .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> shop.setBuyPrice(shop.getBuyPrice() + 10)));
        addElement(new CItem(new ItemCreator(Material.WHITE_BANNER,0)//.addBannerPreset(ItemCreator.BannerPreset.plus, DyeColor.BLACK)
                .setName(ChatColor.YELLOW +"§aAugmenter§f le prix d'achat de §e100" + Eco.devise)).setSlot(25)
                .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> shop.setBuyPrice(shop.getBuyPrice() + 100)));

        addElement(new CItem(new ItemCreator(Material.WHITE_BANNER,0)//.addBannerPreset(ItemCreator.BannerPreset.moin, DyeColor.BLACK)
                .setName(ChatColor.GREEN + "§cDiminuer§f le prix de vente de §e100" + Eco.devise)).setSlot(28)
                .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> shop.setSellPrice(shop.getSellPrice() - 100)));
        addElement(new CItem(new ItemCreator(Material.WHITE_BANNER,0)//.addBannerPreset(ItemCreator.BannerPreset.moin, DyeColor.BLACK)
                .setName(ChatColor.GREEN + "§cDiminuer§f le prix de vente de §e10" + Eco.devise)).setSlot(29)
                .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> shop.setSellPrice(shop.getSellPrice() - 10)));
        addElement(new CItem(new ItemCreator(Material.WHITE_BANNER,0)//.addBannerPreset(ItemCreator.BannerPreset.moin, DyeColor.BLACK)
                .setName(ChatColor.GREEN + "§cDiminuer§f le prix de vente de §e1" + Eco.devise)).setSlot(30)
                .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> shop.setSellPrice(shop.getSellPrice() - 1)));
        addElement(new CItem(new ItemCreator(Material.WHITE_BANNER,0)//.addBannerPreset(ItemCreator.BannerPreset.plus, DyeColor.BLACK)
                .setName(ChatColor.GREEN + "§aAugmenter§f le prix de vente de §e1" + Eco.devise)).setSlot(32)
                .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> shop.setSellPrice(shop.getSellPrice() + 1)));
        addElement(new CItem(new ItemCreator(Material.WHITE_BANNER,0)//.addBannerPreset(ItemCreator.BannerPreset.plus, DyeColor.BLACK)
                .setName(ChatColor.GREEN + "§aAugmenter§f le prix de vente de §e10" + Eco.devise)).setSlot(33)
                .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> shop.setSellPrice(shop.getSellPrice() + 10)));
        addElement(new CItem(new ItemCreator(Material.WHITE_BANNER,0)//.addBannerPreset(ItemCreator.BannerPreset.plus, DyeColor.BLACK)
                .setName(ChatColor.GREEN + "§aAugmenter§f le prix de vente de §e100" + Eco.devise)).setSlot(34)
                .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> shop.setSellPrice(shop.getSellPrice() + 100)));

        update();
    }

    public void update() {
        CItem shopItem = new CItem(new ItemCreator(shop.getItem())).addEvent((inventory, item, player, clickContext) -> {
            ItemStack itemInCursor = player.getItemOnCursor();

            if (itemInCursor.getType().equals(Material.AIR)) {
                if (shop.getAmount() > 0) {
                    ItemStack newItem = new ItemStack(shop.getItem());
                    newItem.setAmount(clickContext.getClickType().equals(ClickType.RIGHT) ? Math.min(newItem.getMaxStackSize(), shop.getAmount()) : 1);
                    shop.setAmount(shop.getAmount() - newItem.getAmount());
                    player.setItemOnCursor(newItem);
                }
            } else {
                ItemStack itemToCompare = new ItemStack(shop.getItem());
                itemToCompare.setAmount(player.getItemOnCursor().getAmount());

                if (itemInCursor.equals(itemToCompare)) {
                    if (shop.getAmount() != shop.getMaxAmount()) {

                        int nbr = clickContext.getClickType().equals(ClickType.RIGHT) ? Math.min(itemInCursor.getAmount(), shop.getMaxAmount() - shop.getAmount()) : 1;
                        itemInCursor.setAmount(itemInCursor.getAmount() - nbr);
                        shop.setAmount(shop.getAmount() + nbr);
                    }
                } else if (shop.getAmount() == 0) {
                    ItemStack newItem = new ItemStack(itemInCursor);
                    newItem.setAmount(1);
                    shop.setItem(newItem);
                }
            }
        }).setSlot(13);
        List<String> lore = new ArrayList<>();
        if (shopItem.getDescription() != null)
            lore.addAll(shopItem.getDescription());
        lore.addAll(Arrays.asList(
                "§f",
                "§fClique §agauche §fpour §brécupérer 1 item",
                "§fClique §adroit §fpour §brécupérer le maximum en main",
                "§fClique §agauche avec du stock §fpour §bdéposer 1 item",
                "§fClique §adroit avec du stock §fpour §bdéposer le maximum en main",
                "§fClique avec un §aitem différent §fpour §achanger le type §f(doit avoir un stock de 0)"
        ));
        shopItem.setDescription(lore);
        addElement(shopItem);
    }
}
