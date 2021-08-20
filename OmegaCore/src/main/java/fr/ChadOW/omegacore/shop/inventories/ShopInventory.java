package fr.ChadOW.omegacore.shop.inventories;

import fr.ChadOW.cinventory.CContent.CInventory;
import fr.ChadOW.cinventory.CContent.CItem;
import fr.ChadOW.cinventory.interfaces.ItemCreator;
import org.bukkit.Material;

public class ShopInventory extends CInventory {
    public ShopInventory(int size, String name) {
        super(size, name);

        int[] white = new int[]{3,4,5,11,12,13,14,15,19,20,22,24,25,28,29,31,33,34,38,39,40,41,42,48,49,50};
        int[] orange = new int[]{0,1,2,6,7,8,9,10,16,17,18,21,23,26,27,30,32,35,36,37,43,44,45,46,47,51,52,53};
        for (int i : white)
            addElement(new CItem(new ItemCreator(Material.WHITE_STAINED_GLASS_PANE, 0)
                    .setName("§f")).setSlot(i));
        for (int i : orange)
            addElement(new CItem(new ItemCreator(Material.ORANGE_STAINED_GLASS_PANE, 0)
                    .setName("§f")).setSlot(i));
    }
}
