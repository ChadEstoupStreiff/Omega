package fr.ChadOW.omegacore.claim;

import com.google.common.collect.Lists;
import fr.ChadOW.api.managers.SQLManager;
import fr.ChadOW.omegacore.P;
import fr.ChadOW.omegacore.claim.commands.ClaimCommand;
import fr.ChadOW.omegacore.claim.commands.UnClaimCommand;
import fr.ChadOW.omegacore.claim.map.MapChunkType;
import fr.ChadOW.omegacore.shop.SerializableShop;
import fr.ChadOW.omegacore.utils.DataUtils;
import fr.ChadOW.omegacore.utils.ServerType;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ClaimManager {
    public final String prefix = "§6[§eOmegaChunk§6] §f";
    private final String dataPath = "claims.dat";
    private final List<Player> showPlayers = new ArrayList<>();
    private final String tableName = "CLAIMS";
    private ArrayList<OmegaChunk> chunks = new ArrayList<>();

    public ClaimManager(P i) {
        loadFromFile();
        i.getCommand("claim").setExecutor(new ClaimCommand());
        i.getCommand("unclaim").setExecutor(new UnClaimCommand());
        i.getServer().getPluginManager().registerEvents(new ClaimListener(), i);

        i.getServer().getScheduler().runTaskTimer(i, () -> {
            if (showPlayers.size() > 0) {
                Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(255, 0, 0), 1);
                for (Player player : showPlayers) {
                    OmegaChunk omegaChunk = getChunk(player.getLocation());

                    int minX = omegaChunk.getX() * 32;
                    int minZ = omegaChunk.getY() * 32;
                    double y = player.getLocation().getY() + 1;

                    for (int iterator = 0; iterator <= 32; iterator ++) {
                        player.spawnParticle(Particle.REDSTONE, minX + iterator, y, minZ, 1, dustOptions);
                        player.spawnParticle(Particle.REDSTONE, minX, y, minZ + iterator, 1, dustOptions);
                        player.spawnParticle(Particle.REDSTONE, minX + iterator, y, minZ + 32, 1, dustOptions);
                        player.spawnParticle(Particle.REDSTONE, minX + 32, y, minZ + iterator, 1, dustOptions);
                    }
                }
            }
        }, 5, 5);
    }

    private OmegaChunk createChunck(World world, int x, int y) {
        OmegaChunk omegaChunk = new OmegaChunk(world, x, y);
        chunks.add(omegaChunk);
        return omegaChunk;
    }

    public OmegaChunk getChunk(Location location) {int x = (int) location.getX()/32;
        int y = (int) location.getZ()/32;
        if (location.getX() < 0) x--;
        if (location.getZ() < 0) y--;
        return getChunk(location.getWorld(), x, y);

    }

    public OmegaChunk getChunk(World world, int x, int y) {
        for (OmegaChunk chunk : chunks) {
            if (chunk.getWorld().equals(world) && chunk.getX() == x && chunk.getY() == y)
                return chunk;
        }
        return createChunck(world, x, y);
    }

    public List<OmegaChunk> getOmegaChunk(String ownerID) {
        ArrayList<OmegaChunk> list = new ArrayList<>();
        for (OmegaChunk chunk : chunks) {
            if (chunk.getOwnerID() != null && chunk.getOwnerID().equals(ownerID))
                list.add(chunk);
        }
        return list;
    }

    public LinkedList<TextComponent> getMap(final Location location, final int height, final int width, String owner) {
        final LinkedList<TextComponent> ret = Lists.newLinkedList();

        ret.add(new TextComponent(" "));

        int halfWidth = width / 2;
        int halfHeight = height / 2;

        OmegaChunk omegaChunk = getChunk(location);
        Chunk topLeft = location.getWorld().getChunkAt(omegaChunk.getX() -halfWidth,omegaChunk.getY() -halfHeight);

        for (int dz = 0; dz < height; dz++) {
            TextComponent line = new TextComponent();

            for (int dx = 0; dx < width; dx++) {
                omegaChunk = getChunk(location.getWorld(), topLeft.getX() + dx, topLeft.getZ() + dz);
                MapChunkType type = MapChunkType.getTypeByChunk(omegaChunk, owner);
                if (dx == halfWidth && dz == halfHeight)
                    type = MapChunkType.AT;

                String overText = "(" + omegaChunk.getX() + ";" + omegaChunk.getY() +"): ";
                switch (type){
                    case AT :
                        overText += "Vous êtes ici !";
                        break;
                    case YOU :
                        overText += "Votre claim ";
                        break;
                    case TAKEN :
                        overText += "Occupé par " + omegaChunk.getOwnerID();
                        break;
                    default:
                        overText += "Libre";
                        break;
                }
                overText = type.getColor() + overText;

                TextComponent text = new TextComponent(String.valueOf(type.getColor()) + '▇');
                text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(overText)));
                line.addExtra(text);
            }
            ret.add(line);
        }

        ret.add(new TextComponent(" "));
        return ret;
    }

    /**
     *
     * Check if a Specific has the permission to do
     * an action in a specific Location
     *
     * @param player
     * @param location
     * @return permission
     */
    public boolean checkPermission(Player player, Location location) {
        OmegaChunk chunk = getChunk(location);
        return chunk.getOwnerID() != null && chunk.getOwnerID().equalsIgnoreCase(player.getUniqueId().toString());
    }

    public void switchShow(Player player) {
        if (showPlayers.contains(player)) {
            showPlayers.remove(player);
            player.sendMessage(prefix + "Visualisation des parcelles de terre désactivé.");
        }
        else {
            showPlayers.add(player);
            player.sendMessage(prefix + "Visualisation des parcelles de terre activé.");
        }
    }

    public void setShowOff(Player player) {
        showPlayers.remove(player);
    }

    public void setShowOn(Player player) {
        if (!showPlayers.contains(player))
            showPlayers.add(player);
    }




    public void getFromDb() {
        P.getInstance().getSender().sendMessage("[Claims] Loading ..");
        SQLManager.getInstance().query("SELECT * FROM " + tableName + " WHERE server='" + ServerType.getServerType() + "'", rs -> {
            try {
                chunks = new ArrayList<>();
                while (rs.next()) {
                    World world = Bukkit.getWorld(rs.getString("world"));
                    if (world != null) {
                        new OmegaChunk(
                                world,
                                rs.getInt("x"),
                                rs.getInt("y"))
                                .setOwnerID(rs.getString("ownerID"));
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    public void save() {
        saveToFile();
        saveToDB();
    }

    public void saveToDB() {
        P.getInstance().getSender().sendMessage("[Claims] Saving to DB...");
        SQLManager sql = SQLManager.getInstance();
        for (OmegaChunk chunk : chunks) {
            if (chunk.getOwnerID() != null)
                sql.query("SELECT * FROM " + tableName + " WHERE server='" + ServerType.getServerType() + "' AND world='" + chunk.getWorld().toString() + "' AND x=" + chunk.getX() + " AND y=" + chunk.getY(), rs -> {
                    try {
                        if (rs.next()) {
                            if (chunk.getOwnerID() != null)
                                sql.update("UPDATE " + tableName + " SET ownerID='" + chunk.getOwnerID() + "' WHERE server='" + ServerType.getServerType() + "' AND world='" + chunk.getWorld().getName() + "' AND x=" + chunk.getX() + " AND y=" + chunk.getY());
                            else
                                sql.update("DELETE FROM " + tableName + " WHERE server='" + ServerType.getServerType() + "' AND world='" + chunk.getWorld().getName() + "' AND x=" + chunk.getX() + " AND y=" + chunk.getY());
                        } else {
                            if (chunk.getOwnerID() != null)
                                sql.update("INSERT INTO " + tableName + "(server, world, x, y, ownerID) VALUES ('" + ServerType.getServerType() + "', '" + chunk.getWorld().getName() + "', " + chunk.getX() + ", " + chunk.getY() + ", '" + chunk.getOwnerID() + "')");
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                });
        }
    }

    private void loadFromFile() {
        P.getInstance().getSender().sendMessage("[Claims] Loading ...");
        List<SeriazableOmegaChunk> claimsData = new DataUtils<SeriazableOmegaChunk>().readData(dataPath, SeriazableOmegaChunk.class);
        claimsData.forEach(claim -> claim.createOmegaChunk(this));
    }

    public void saveToFile() {
        P.getInstance().getSender().sendMessage("[Claims] Saving ...");
        List<SeriazableOmegaChunk> claimsData = new ArrayList<>();
        new ArrayList<>(chunks).forEach(chunk -> {
            if (chunk.getOwnerID() != null)
                claimsData.add(new SeriazableOmegaChunk(chunk));
        });
        new DataUtils<SeriazableOmegaChunk>().saveData(dataPath, claimsData);
    }
}
