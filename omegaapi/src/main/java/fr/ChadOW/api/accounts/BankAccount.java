package fr.ChadOW.api.accounts;

import fr.ChadOW.api.managers.JedisManager;
import fr.ChadOW.api.managers.OmegaAPIUtils;
import fr.ChadOW.api.managers.SQLManager;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class BankAccount {
    private final String ID;
    private double amount;
    private String name;

    public BankAccount(String id, double amount, String name) {
        ID = id;
        this.amount = amount;
        this.name = name;
        sendToRedis();
    }

    public String getID() {
        return ID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = OmegaAPIUtils.roundDouble(amount);
        sendToRedis();
    }

    public void addAmount(double amount) {
        setAmount(getAmount() + amount);
    }

    public void removeAmount(double amount) {
        setAmount(getAmount() - amount);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        sendToRedis();
    }

    //USEFULL FCT

    public void payAccount(double amount, BankAccount target) {
        this.removeAmount(amount);
        target.addAmount(amount);
        printBillToDb(amount, this, target);
    }

    private void printBillToDb(double amount, BankAccount payer, BankAccount receiver) {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss"));
        SQLManager.getInstance().update("INSERT INTO BILLS (date, amount, payerID, receiverID, newSoldPayer, newSoldReceiver) VALUES ('" + date + "', " + amount + ", '" + payer.getID() + "', '" + receiver.getID() + "', " + payer.getAmount() + ", " + receiver.getAmount() + ")");
    }

    //ALL DATABASE FCT

    public static List<BankAccount> getAllAccounts() {
        ArrayList<BankAccount> accounts = new ArrayList<>();
        for (String key : JedisManager.getInstance().getKeys("BankAccount:*")) {
            accounts.add(getAccountFromRedis(key.substring(12)));
        }
        return accounts;
    }

    public void sendToRedis() {
        JedisManager.getInstance().setValue("BankAccount:" + getID(), JedisManager.getGson().toJson(this));
    }

    public void sendToDb() {
        SQLManager.getInstance().update("UPDATE BANKS SET amount=" + getAmount() + ", name='" + getName() + "' WHERE bankID='" + getID() + "'");
    }

    public static BankAccount getAccount(String id) {
        return getAccountFromRedis(id);
    }

    private static BankAccount getAccountFromRedis(String id) {
        String value = JedisManager.getInstance().getValue("BankAccount:" + id);

        if (value != null)
            return JedisManager.getGson().fromJson(value, BankAccount.class);
        else
            return getAccountFromDb(id);
    }

    public static BankAccount getAccountFromDb(String id) {
        AtomicReference<BankAccount> account = new AtomicReference<>();

        SQLManager sql = SQLManager.getInstance();
        sql.query("SELECT * FROM BANKS WHERE bankID='" + id + "'", rs -> {
            try {
                if (rs.next()) {
                    account.set(new BankAccount(
                            rs.getString("bankID"),
                            rs.getDouble("amount"),
                            rs.getString("name")
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

    private static BankAccount createAccount(String id) {
        BankAccount bankAccount = new BankAccount(id, 0, id);
        bankAccount.initInDb();
        System.out.println("[OmegaAPI] BankAccount> compte " + id + " created");
        return bankAccount;
    }

    private void initInDb() {
        SQLManager.getInstance().update("INSERT INTO BANKS (bankID, amount, name) VALUES ('" + getID() + "', " + getAmount() + ", '" + getName() + "')");
    }
}
