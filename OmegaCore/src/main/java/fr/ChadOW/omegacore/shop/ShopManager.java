package fr.ChadOW.omegacore.shop;

import fr.ChadOW.omegacore.P;
import fr.ChadOW.omegacore.shop.commands.CommandShop;
import org.bukkit.entity.Horse;

import java.util.HashMap;
import java.util.Map;

public class ShopManager {

    public static final Map<Horse,Shop> shops = new HashMap<>();

    public ShopManager(P i) {
        i.getCommand("shop").setExecutor(new CommandShop());
        i.getServer().getPluginManager().registerEvents(new ShopListener(),i);
        loadShops();
    }

    private static void loadShops(){
        //todo
    }
}
