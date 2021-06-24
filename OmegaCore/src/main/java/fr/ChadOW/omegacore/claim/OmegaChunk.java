package fr.ChadOW.omegacore.claim;

import fr.ChadOW.api.managers.SQLManager;
import fr.ChadOW.omegacore.P;
import fr.ChadOW.omegacore.utils.ServerType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OmegaChunk {
    private static final String tableName = "CLAIMS";
    private static ArrayList<OmegaChunk> chunks = new ArrayList<>();

    public static OmegaChunk getChunk(Location location) {int x = (int) location.getX()/32;
        int y = (int) location.getZ()/32;
        if (location.getX() < 0) x--;
        if (location.getZ() < 0) y--;
        return getChunk(location.getWorld(), x, y);

    }

    public static OmegaChunk getChunk(World world, int x, int y) {
        for (OmegaChunk chunk : chunks) {
            if (chunk.getWorld().equals(world) && chunk.getX() == x && chunk.getY() == y)
                return chunk;
        }
        return new OmegaChunk(world, x, y);
    }

    public static List<OmegaChunk> getOmegaChunk(String ownerID) {
        ArrayList<OmegaChunk> list = new ArrayList<>();
        for (OmegaChunk chunk : chunks) {
            if (chunk.getOwnerID() != null && chunk.getOwnerID().equals(ownerID))
                list.add(chunk);
        }
        return list;
    }

    private final World world;
    private final int x, y;
    private String ownerID;

    private OmegaChunk(World world, int x, int y) {
        this.world = world;
        this.x = x;
        this.y = y;

        chunks.add(this);
    }

    public World getWorld() {
        return world;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }


    @Override
    public String toString() {
        return "Monde: " + Bukkit.getServer().getName() + ":" + world.getName() + ", X: " + getX() + ", Y: " + getY() + ", Propriétaire: " + getOwnerID();
    }


    public static void getFromDb() {
        P.getSender().sendMessage("Loading claims ..");
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

    public static void saveToDB() {
        SQLManager sql = SQLManager.getInstance();
        for (OmegaChunk chunk : chunks) {
            if (chunk.getOwnerID() != null)
                sql.query("SELECT * FROM " + tableName + " WHERE server='" + ServerType.getServerType() + "' AND world='" + chunk.getWorld().toString() + "' AND x=" + chunk.getX() + " AND y=" + chunk.getY(), rs -> {
                    try {
                        if (rs.next()) {
                            sql.update("UPDATE " + tableName + " SET ownerID='" + chunk.getOwnerID() + "' WHERE server='" + ServerType.getServerType() + "' AND world='" + chunk.getWorld().getName() + "' AND x=" + chunk.getX() + " AND y=" + chunk.getY());
                        } else {
                            sql.update("INSERT INTO " + tableName + "(server, world, x, y, ownerID) VALUES ('" + ServerType.getServerType() + "', '" + chunk.getWorld().getName() + "', " + chunk.getX() + ", " + chunk.getY() + ", '" + chunk.getOwnerID() + "')");
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                });
        }
    }
}
