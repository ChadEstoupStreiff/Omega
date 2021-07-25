package fr.ChadOW.omegacore.shop;

import fr.ChadOW.omegacore.P;
import fr.ChadOW.omegacore.shop.commands.CommandShop;
import fr.ChadOW.omegacore.utils.DataUtils;
import org.bukkit.Location;
import org.bukkit.entity.Horse;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ShopManager {

    private final Map<Horse,Shop> shops = new HashMap<>();
    private final String dataPath = "shops.dat";

    public ShopManager(P i) {
        Objects.requireNonNull(i.getCommand("shop")).setExecutor(new CommandShop());
        i.getServer().getPluginManager().registerEvents(new ShopListener(),i);
        loadShops(i);
    }

    private void loadShops(P i){
        i.getSender().sendMessage("[Shops] Loading ...");
        List<ShopData> shopData = new DataUtils<ShopData>().readData(dataPath, ShopData.class);
        shopData.forEach(shop -> shop.createShop(this));
    }

    public void saveShops(P i) {
        i.getSender().sendMessage("[Shops] Saving ...");
        List<ShopData> shopData = new ArrayList<>();
        shops.values().forEach(shop -> {
            shopData.add(new ShopData(shop));
            destroyShop(shop);
        });
        new DataUtils<ShopData>().saveData(dataPath, shopData);
    }

    public Shop createShop(Location location, ItemStack item, int buyPrice, int sellPrice, int amount, UUID owner){
        Shop shop = new Shop(location, item, buyPrice, sellPrice, amount, owner);
        shop.updateShop();
        shops.put(shop.getHorse(),shop);
        return shop;
    }

    public void destroyShop(Shop shop){
        shop.getHorse().remove();
        shop.getShopDisplayItem().remove();
        shops.remove(shop.getHorse());
    }

    public Map<Horse, Shop> getShops() {
        return shops;
    }
}
