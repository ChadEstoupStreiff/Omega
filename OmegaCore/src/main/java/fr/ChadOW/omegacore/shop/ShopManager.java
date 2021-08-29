package fr.ChadOW.omegacore.shop;

import fr.ChadOW.omegacore.P;
import fr.ChadOW.omegacore.shop.commands.CommandShop;
import fr.ChadOW.omegacore.utils.DataUtils;
import org.bukkit.Location;
import org.bukkit.entity.Bee;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ShopManager {

    private final ArrayList<Shop> shops = new ArrayList<>();
    private final String dataPath = "shops.dat";

    public ShopManager(P i) {
        i.getCommand("shop").setExecutor(new CommandShop());
        i.getServer().getPluginManager().registerEvents(new ShopListener(),i);
        loadShops();
    }

    private void loadShops() {
        P.getInstance().getSender().sendMessage("[Shops] Loading ...");
        List<SerializableShop> shopsData = new DataUtils<SerializableShop>().readData(dataPath, SerializableShop.class);
        shopsData.forEach(shop -> shop.createShop(this));
    }

    public void saveShops() {
        P.getInstance().getSender().sendMessage("[Shops] Saving ...");
        List<SerializableShop> shopsData = new ArrayList<>();
        new ArrayList<>(shops).forEach(shop -> {
            shopsData.add(new SerializableShop(shop));
            destroyShop(shop);
        });
        new DataUtils<SerializableShop>().saveData(dataPath, shopsData);
    }

    public void createShop(Location location, ItemStack item, int buyPrice, int sellPrice, int amount, boolean adminShop, UUID owner){
        Shop shop = new Shop(location, item, buyPrice, sellPrice, amount, adminShop, owner);
        shops.add(shop);
    }

    public void destroyShop(Shop shop){
        shop.delete();
        shops.remove(shop);
    }

    public Shop getShop(Bee entity) {
        for (Shop shop : shops) {
            if (shop.getBee().equals(entity))
                return shop;
        }
        return null;
    }
}
