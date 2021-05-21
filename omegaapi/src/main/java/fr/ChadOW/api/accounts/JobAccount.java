package fr.ChadOW.api.accounts;

import fr.ChadOW.api.enums.Job;
import fr.ChadOW.api.managers.JedisManager;
import fr.ChadOW.api.managers.OmegaAPIUtils;
import fr.ChadOW.api.managers.SQLManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class JobAccount {
    private final String ID;
    private String job;
    private int level;
    private double exp;

    public JobAccount(String id, String job, int level, double exp) {
        ID = id;
        this.job = job;
        this.level = level;
        this.exp = exp;
        sendToRedis();
    }

    public String getID() {
        return ID;
    }

    public Job getJob() {
        return Job.valueOf(job);
    }

    public void setJob(Job job) {
        this.level = 0;
        this.exp = 0;
        this.job = job.toString();
        sendToRedis();
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        sendToRedis();
    }

    public double getExp() {
        return exp;
    }

    public void setExp(double exp) {
        this.exp = exp;

        int xpNeeded = getExpNeededToNextLevel();
        while (xpNeeded > 0 && this.exp >= xpNeeded) {
            this.exp -= xpNeeded;
            this.level++;
        }
        sendToRedis();
    }

    public void addExp(double exp) {
        setExp(getExp() + exp);
    }

    //USEFULL FCT

    public int getExpNeededToNextLevel() {
        return (int)(getJob().getBase() * Math.pow(Job.getExpMultiplicator(), getLevel()));
    }

    public double applyMoneyBonus(double i) {
        return OmegaAPIUtils.roundDouble(i * (1 + getLevel()*Job.getMoneyMultiplicator()));
    }

    //ALL DATABASE FCT

    public static List<JobAccount> getAllAccounts() {
        ArrayList<JobAccount> accounts = new ArrayList<>();
        for (String key : JedisManager.getInstance().getKeys("JobAccount:*")) {
            accounts.add(getAccountFromRedis(key.substring(11)));
        }
        return accounts;
    }

    public void sendToRedis() {
        JedisManager.getInstance().setValue("JobAccount:" + getID(), JedisManager.getGson().toJson(this));
    }

    public void sendToDb() {
        SQLManager.getInstance().update("UPDATE JOBS SET type='" + getJob().toString() + "', level=" + getLevel() + ", exp=" + getExp() + " WHERE jobID='" + getID() + "'");
    }

    public static JobAccount getAccount(String id) {
        return getAccountFromRedis(id);
    }

    private static JobAccount getAccountFromRedis(String id) {

        String value = JedisManager.getInstance().getValue("JobAccount:" + id);

        if (value != null)
            return JedisManager.getGson().fromJson(value, JobAccount.class);
        else
            return getAccountFromDb(id);
    }

    public static JobAccount getAccountFromDb(String id) {
        AtomicReference<JobAccount> account = new AtomicReference<>();

        SQLManager sql = SQLManager.getInstance();
        sql.query("SELECT * FROM JOBS WHERE jobID='" + id + "'", rs -> {
            try {
                if (rs.next()) {
                    account.set(new JobAccount(
                            rs.getString("jobID"),
                            rs.getString("type"),
                            rs.getInt("level"),
                            rs.getDouble("exp")
                    ));
                } else {
                    account.set(createAccount(id));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return account.get();
    }

    private static JobAccount createAccount(String id) {
        JobAccount jobAccount = new JobAccount(id, Job.getDefaultJob().toString(), 0, 0);
        jobAccount.initInDb();
        System.out.println("[OmegaAPI] JobAccount> compte " + id + " created");
        return jobAccount;
    }

    private void initInDb() {
        SQLManager.getInstance().update("INSERT INTO JOBS (jobID, type, level, exp) VALUES ('" + getID() + "', '" + getJob() + "', " + getLevel() + ", " + getExp() + ")");
    }
}
