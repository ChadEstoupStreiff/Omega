package fr.ChadOW.omegacore.shop.inventories;

import fr.ChadOW.cinventory.CContent.CItem;
import fr.ChadOW.cinventory.interfaces.ItemCreator;
import fr.ChadOW.omegacore.shop.Shop;
import org.bukkit.Material;

public class AdminInventory extends ShopInventory {
    public AdminInventory(Shop shop) {
        super(6*9, "§§8§lAdministration du magasin");

        addElement(new CItem(new ItemCreator(Material.REPEATER, 0)
                .setName("§cMenu édition")).setSlot(53)
                .addEvent((inventory, item1, player, clickContext) -> shop.getConfigurationInventory().open(player)));

        addElement(new CItem(new ItemCreator(Material.GRAY_DYE, 0).setName("§fMode administrateur: §cDésactivé"))
                .addEvent((inventory, item, player, clickContext) -> {
                    shop.setAdminShop(!shop.isAdminShop());
                    if (shop.isAdminShop())
                        ((CItem)item).setMaterial(Material.LIME_DYE).setName("§fMode administrateur: §aActivé");
                    else
                        ((CItem)item).setMaterial(Material.GRAY_DYE).setName("§fMode administrateur: §cDésactivé");
                    ((CItem) item).updateDisplay();
                })
                .setSlot(22));
    }
}
