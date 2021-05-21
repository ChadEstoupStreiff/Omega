package fr.ChadOW.api.bungee;

import fr.ChadOW.api.accounts.BankAccount;
import fr.ChadOW.api.accounts.JobAccount;
import fr.ChadOW.api.accounts.UserAccount;
import fr.ChadOW.api.accounts.group.Group;
import fr.ChadOW.api.accounts.group.Member;
import fr.ChadOW.api.bungee.global.Taxes;
import fr.ChadOW.api.bungee.listeners.PlayerJoinQuit;
import fr.ChadOW.api.enums.Rank;
import fr.ChadOW.api.managers.JedisManager;
import fr.ChadOW.api.managers.OmegaAPIUtils;
import fr.ChadOW.api.managers.SQLManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


public class BungeeAPI extends Plugin {
    private static BungeeAPI INSTANCE;
    private SQLManager mysql;
    private JedisManager jedisManager;

    public void onEnable() {
        INSTANCE = this;

        createFile("config");

        Configuration configuration = null;
        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        initMySQL(configuration);
        initRedis(configuration);
        sendAllDbDataInRedis();
        initGlobal();
    }

    private void sendAllDbDataInRedis() {
        SQLManager sql = SQLManager.getInstance();

        sql.query("SELECT * FROM USERS", rs -> {
            try {
                int counter = 0;
                while (rs.next()) {
                    new UserAccount(
                            UUID.fromString(rs.getString("uuid")),
                            Rank.valueOf(rs.getString("rank")),
                            rs.getDouble("credits"),
                            rs.getString("bankID"),
                            rs.getString("jobID"),
                            rs.getString("groupID")
                    );
                    counter++;
                }
                System.out.println("(" + counter + ") user accounts transferred !");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        sql.query("SELECT * FROM BANKS", rs -> {
            try {
                int counter = 0;
                while (rs.next()) {
                    new BankAccount(
                            rs.getString("bankID"),
                            rs.getDouble("amount"),
                            rs.getString("name")
                    );
                    counter++;
                }
                System.out.println("(" + counter + ") bank accounts transferred !");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        sql.query("SELECT * FROM JOBS", rs -> {
            try {
                int counter = 0;
                while (rs.next()) {
                    new JobAccount(
                            rs.getString("jobID"),
                            rs.getString("type"),
                            rs.getInt("level"),
                            rs.getDouble("exp")
                    );
                    counter++;
                }
                System.out.println("(" + counter + ") job accounts transferred !");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        sql.query("SELECT * FROM GROUPS", rs -> {
            try {
                int counter = 0;
                while (rs.next()) {
                    new Group(
                            rs.getString("groupID"),
                            rs.getString("name"),
                            rs.getString("bankID"),
                            new Member(UUID.fromString(rs.getString("chef")), Member.HIERARCHY.LEADER),
                            JedisManager.getGson().fromJson(rs.getString("members"), ArrayList.class)
                    );
                    counter++;
                }
                System.out.println("(" + counter + ") groups transferred !");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void initGlobal() {
        getProxy().getScheduler().schedule(this, Taxes::launchTaxesCheck, 12, 12, TimeUnit.HOURS);

        Taxes.launchTaxesCheck();


        ProxyServer.getInstance().getPluginManager().registerListener(this, new PlayerJoinQuit(this));
        getProxy().getScheduler().schedule(this, () -> {
            System.out.println("Saving all accounts ...");
            for (UserAccount userAccount : UserAccount.getAllAccounts()) {
                userAccount.sendToDb();
            }
            for (BankAccount bankAccount : BankAccount.getAllAccounts()) {
                bankAccount.sendToDb();
            }
            for (JobAccount jobAccount : JobAccount.getAllAccounts()) {
                jobAccount.sendToDb();
            }
            System.out.println("All accounts saved.");
            OmegaAPIUtils.getData();
            //TODO Save régulièrement tout les groupes
        }, 2, 2, TimeUnit.MINUTES);
    }

    public void onDisable() {
        (JedisManager.getInstance()).poolJedis.close();
        this.mysql.closePool();
    }

    public static BungeeAPI getInstance() {
        return INSTANCE;
    }

    private void initRedis(Configuration configuration) {
        this.jedisManager = new JedisManager(configuration.getString("redis.hostname"), configuration.getInt("redis.port"), configuration.getString("redis.password"));
    }

    private JedisManager getJedis() {
        return this.jedisManager;
    }

    private void initMySQL(Configuration configuration) {
        
        this.mysql = new SQLManager(configuration.getString("mysql.hostname"), configuration.getInt("mysql.port"), configuration.getString("mysql.database"), configuration.getString("mysql.user"), configuration.getString("mysql.password"));
    }

    public SQLManager getMySQL() {
        return this.mysql;
    }

    private void createFile(String fileName) {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        File file = new File(getDataFolder(), fileName + ".yml");

        if (!file.exists()) {
            try {
                file.createNewFile();

                if (fileName.equals("config")) {
                    Configuration config = getConfig(fileName);
                    config.set("mysql.hostname", "127.0.0.1");
                    config.set("mysql.port", 3306);
                    config.set("mysql.user", "root");
                    config.set("mysql.password", "password");
                    config.set("mysql.database", "accounts");
                    config.set("redis.hostname", "127.0.0.1");
                    config.set("redis.port", 6379);
                    config.set("redis.password", "password");

                    saveConfig(config, fileName);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Configuration getConfig(String fileName) {
        try {
            return ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), fileName + ".yml"));
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }

    private void saveConfig(Configuration config, String fileName) {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, new File(getDataFolder(), fileName + ".yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}