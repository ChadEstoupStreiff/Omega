package fr.ChadOW.api.accounts;

import fr.ChadOW.api.accounts.group.Group;
import fr.ChadOW.api.accounts.group.Member;
import fr.ChadOW.api.enums.Rank;
import fr.ChadOW.api.managers.JedisManager;
import fr.ChadOW.api.managers.OmegaAPIUtils;
import fr.ChadOW.api.managers.SQLManager;
import org.bukkit.Bukkit;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class UserAccount {

    private final UUID uuid;
    private Rank rank;
    private final String bankID;
    private final String jobID;
    private String groupID;
    private double credits;

    public UserAccount(UUID uuid, Rank rank, double credits, String bankID, String jobID, String groupID) {
        this.uuid = uuid;
        this.rank = rank;
        this.credits = credits;
        this.bankID = bankID;
        this.jobID = jobID;
        this.groupID = groupID;
        sendToRedis();
    }

    public UUID getUUID() {
        return uuid;
    }

    public Rank getRank() {
        return rank;
    }

    public String getBankID() {
        return bankID;
    }

    public BankAccount getBankAccount() {
        return BankAccount.getAccount(getBankID());
    }

    public String getJobID() {
        return jobID;
    }

    public JobAccount getJobAccount() {
        return JobAccount.getAccount(getJobID());
    }

    public String getGroupID() {
        return groupID;
    }

    public Group getGroup() {
        if (getGroupID() != null)
            return Group.getGroup(getGroupID());
        return null;
    }

    public double getCredits() {
        return credits;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
        sendToRedis();
    }

    public void setGroup(Group group) {
        if (getGroup() != null) {
            Group old = getGroup();
            old.removeMember(old.getMember(uuid));
        }
        this.groupID = group.getID();
        group.addMember(new Member(uuid, Member.HIERARCHY.VISITOR));
        sendToRedis();
    }

    public void setCredits(double credits) {
        this.credits = credits;
        sendToRedis();
    }

    //ALL DATABASE FUNCTIONS

    public static List<UserAccount> getAllAccounts() {
        ArrayList<UserAccount> accounts = new ArrayList<>();

        for (String key : JedisManager.getInstance().getKeys("UserAccount:*")) {
            accounts.add(getAccountFromRedis(UUID.fromString(key.substring(12))));
        }
        return accounts;
    }

    public void sendToRedis() {
        JedisManager.getInstance().setValue("UserAccount:" + getUUID().toString(), JedisManager.getGson().toJson(this));
    }

    public void sendToDb() {
        SQLManager.getInstance().update("UPDATE USERS SET rank='" + getRank().toString() + "', credits=" + getCredits() + ", bankID='" + getBankID() + "', jobID='" + getJobID() + "' WHERE uuid='" + getUUID().toString() + "'");
    }

    public static UserAccount getAccount(UUID uuid) {
        return getAccountFromRedis(uuid);
    }

    private static UserAccount getAccountFromRedis(UUID uuid) {
        String value = JedisManager.getInstance().getValue("UserAccount:" + uuid);

        if (value != null)
            return JedisManager.getGson().fromJson(value, UserAccount.class);
        else
            return getAccountFromDb(uuid);
    }

    public static UserAccount getAccountFromDb(UUID uuid) {
        AtomicReference<UserAccount> account = new AtomicReference<>();

        SQLManager sql = SQLManager.getInstance();
        sql.query("SELECT * FROM USERS WHERE uuid='" + uuid + "'", rs -> {
            try {
                if (rs.next()) {
                    account.set(new UserAccount(
                            UUID.fromString(rs.getString("uuid")),
                            Rank.valueOf(rs.getString("rank")),
                            rs.getDouble("credits"),
                            rs.getString("bankID"),
                            rs.getString("jobID"),
                            rs.getString("groupID")
                    ));
                } else {
                    account.set(createAccount(uuid));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return account.get();
    }

    private static UserAccount createAccount(UUID uuid) {
        UserAccount userAccount = new UserAccount(uuid, Rank.getDefaultRank(), 0, "J" + uuid, "J" + uuid, "null");
        userAccount.initInDb();
        userAccount.getBankAccount().setName(OmegaAPIUtils.tryToConvertIDToStringByUserAccount(uuid.toString()));
        System.out.println("[OmegaAPI] UserAccount> compte " + uuid + " created");
        return userAccount;
    }

    private void initInDb() {
        SQLManager.getInstance().update("INSERT INTO USERS (uuid, rank, credits, bankID, jobID, groupID) VALUES ('" + getUUID() + "', '" + getRank() + "', " + getCredits() + ", '" + getBankID() + "', '" + getJobID() + "', '" + getGroupID() + "')");
    }
}
