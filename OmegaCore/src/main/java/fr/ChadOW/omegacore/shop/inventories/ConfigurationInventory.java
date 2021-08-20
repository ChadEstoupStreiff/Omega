package fr.ChadOW.omegacore.shop.inventories;

import fr.ChadOW.cinventory.CContent.CItem;
import fr.ChadOW.cinventory.interfaces.ItemCreator;
import fr.ChadOW.omegacore.shop.Shop;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class ConfigurationInventory extends ShopInventory {
    public ConfigurationInventory(Shop shop) {
        super(6*9, "§§8§lEdition du magasin");

        addElement(shop.getMenuInventory().getShopItem());
        addElement(shop.getMenuInventory().getBuyItem());
        addElement(shop.getMenuInventory().getSellItem());
        addElement(shop.getMenuInventory().getQuantityAvailable());

        addElement(new CItem(new ItemCreator(Material.WHITE_BANNER,0)//.addBannerPreset(ItemCreator.BannerPreset.moin, DyeColor.BLACK)
                .setName(ChatColor.YELLOW +"§cDiminuer§f le prix d'achat de §e100$")).setSlot(19)
                .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> shop.setBuyPrice(shop.getBuyPrice() - 100)));
        addElement(new CItem(new ItemCreator(Material.WHITE_BANNER,0)//.addBannerPreset(ItemCreator.BannerPreset.moin, DyeColor.BLACK)
                .setName(ChatColor.YELLOW +"§cDiminuer§f le prix d'achat de §e10$")).setSlot(20)
                .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> shop.setBuyPrice(shop.getBuyPrice() - 10)));
        addElement(new CItem(new ItemCreator(Material.WHITE_BANNER,0)//.addBannerPreset(ItemCreator.BannerPreset.moin, DyeColor.BLACK)
                .setName(ChatColor.YELLOW +"§cDiminuer§f le prix d'achat de §e1$")).setSlot(21)
                .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> shop.setBuyPrice(shop.getBuyPrice() - 1)));
        addElement(new CItem(new ItemCreator(Material.WHITE_BANNER,0)//.addBannerPreset(ItemCreator.BannerPreset.plus, DyeColor.BLACK)
                .setName(ChatColor.YELLOW +"§aAugmenter§f le prix d'achat de §e1$")).setSlot(23)
                .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> shop.setBuyPrice(shop.getBuyPrice() + 1)));
        addElement(new CItem(new ItemCreator(Material.WHITE_BANNER,0)//.addBannerPreset(ItemCreator.BannerPreset.plus, DyeColor.BLACK)
                .setName(ChatColor.YELLOW +"§aAugmenter§f le prix d'achat de §e10$")).setSlot(24)
                .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> shop.setBuyPrice(shop.getBuyPrice() + 10)));
        addElement(new CItem(new ItemCreator(Material.WHITE_BANNER,0)//.addBannerPreset(ItemCreator.BannerPreset.plus, DyeColor.BLACK)
                .setName(ChatColor.YELLOW +"§aAugmenter§f le prix d'achat de §e100$")).setSlot(25)
                .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> shop.setBuyPrice(shop.getBuyPrice() + 100)));

        addElement(new CItem(new ItemCreator(Material.WHITE_BANNER,0)//.addBannerPreset(ItemCreator.BannerPreset.moin, DyeColor.BLACK)
                .setName(ChatColor.GREEN + "§cDiminuer§f le prix de vente de §e100$")).setSlot(28)
                .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> shop.setSellPrice(shop.getSellPrice() - 100)));
        addElement(new CItem(new ItemCreator(Material.WHITE_BANNER,0)//.addBannerPreset(ItemCreator.BannerPreset.moin, DyeColor.BLACK)
                .setName(ChatColor.GREEN + "§cDiminuer§f le prix de vente de §e10$")).setSlot(29)
                .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> shop.setSellPrice(shop.getSellPrice() - 10)));
        addElement(new CItem(new ItemCreator(Material.WHITE_BANNER,0)//.addBannerPreset(ItemCreator.BannerPreset.moin, DyeColor.BLACK)
                .setName(ChatColor.GREEN + "§cDiminuer§f le prix de vente de §e1$")).setSlot(30)
                .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> shop.setSellPrice(shop.getSellPrice() - 1)));
        addElement(new CItem(new ItemCreator(Material.WHITE_BANNER,0)//.addBannerPreset(ItemCreator.BannerPreset.plus, DyeColor.BLACK)
                .setName(ChatColor.GREEN + "§aAugmenter§f le prix de vente de §e1$")).setSlot(32)
                .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> shop.setSellPrice(shop.getSellPrice() + 1)));
        addElement(new CItem(new ItemCreator(Material.WHITE_BANNER,0)//.addBannerPreset(ItemCreator.BannerPreset.plus, DyeColor.BLACK)
                .setName(ChatColor.GREEN + "§aAugmenter§f le prix de vente de §e10$")).setSlot(33)
                .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> shop.setSellPrice(shop.getSellPrice() + 10)));
        addElement(new CItem(new ItemCreator(Material.WHITE_BANNER,0)//.addBannerPreset(ItemCreator.BannerPreset.plus, DyeColor.BLACK)
                .setName(ChatColor.GREEN + "§aAugmenter§f le prix de vente de §e100$")).setSlot(34)
                .addEvent((inventoryRepresentation, itemRepresentation, p, clickContext) -> shop.setSellPrice(shop.getSellPrice() + 100)));

        addElement(new CItem(new ItemCreator(Material.REPEATER,0)
                .setName("§eRevenir au magasin")).setSlot(53)
                .addEvent((inventoryRepresentation, itemRepresentation, player, clickContext) -> shop.getMenuInventory().open(player)));
    }
}
