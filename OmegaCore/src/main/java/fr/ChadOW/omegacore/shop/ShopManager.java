package fr.ChadOW.omegacore.shop;

import fr.ChadOW.omegacore.P;
import fr.ChadOW.omegacore.shop.commands.CommandShop;
import org.bukkit.Location;
import org.bukkit.entity.Horse;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.util.*;

public class ShopManager {

    private final Map<Horse,Shop> shops = new HashMap<>();
    private final String dataPath = "shops" + File.separator +"shop";

    public ShopManager(P i) {
        Objects.requireNonNull(i.getCommand("shop")).setExecutor(new CommandShop());
        i.getServer().getPluginManager().registerEvents(new ShopListener(),i);
        loadShops(i);
    }

    private void loadShops(P i){
        i.getSender().sendMessage("[Shops] Loading ...");
        File f = new File("shops");
        String[] pathNames = f.list();
        for (String pathname : Objects.requireNonNull(pathNames)){
            try {
                SerializableShop serializableShop = (SerializableShop) loadObject("shops" + File.separator + pathname);
                serializableShop.createShop(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void saveShops(P i) {
        i.getSender().sendMessage("[Shops] Saving ...");
        shops.values().forEach(shop -> {
            try {
                saveObject(new SerializableShop(shop),dataPath + shop.getId() + ".dat");
            } catch (Exception e) {
                e.printStackTrace();
            }
            destroyShop(shop);
        });
    }

    public Shop createShop(Location location, ItemStack item, int buyPrice, int sellPrice, int amount, UUID owner){
        Shop shop = new Shop(location, item, buyPrice, sellPrice, amount, owner);
        shop.updateShop();
        shops.put(shop.getHorse(),shop);
        shop.setId(new ArrayList<>(shops.values()).indexOf(shop));
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

    //
    // Save Object Into A File
    //
    public void saveObject(Object obj, String path) throws Exception {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
        oos.writeObject(obj);
        oos.flush();
        oos.close();
    }

    //
    // Load Object From A File
    //
    public Object loadObject(String path) throws Exception {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
        Object result = ois.readObject();
        ois.close();
        return result;
    }
}
