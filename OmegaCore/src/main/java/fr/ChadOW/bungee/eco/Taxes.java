package fr.ChadOW.bungee.eco;

import fr.ChadOW.api.accounts.BankAccount;
import fr.ChadOW.api.accounts.UserAccount;
import fr.ChadOW.api.managers.JedisManager;
import fr.ChadOW.api.managers.SQLManager;
import fr.ChadOW.bungee.Bungee;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

public class Taxes {
    private static final String prefix = "[Taxes] ";

    private static LocalDate lastTaxes;

    public static void init(Bungee bungee) {
        bungee.getProxy().getScheduler().schedule(bungee, Taxes::launchTaxesCheck, 12, 12, TimeUnit.HOURS);
        Taxes.launchTaxesCheck();
    }

    public static void launchTaxesCheck() {
        System.out.println(prefix + "Checking ...");
        SQLManager.getInstance().query("SELECT value FROM GLOBALDATAS WHERE data='lastTaxes'", rs -> {
            try {
                if (rs.next()) {
                    lastTaxes = JedisManager.getGson().fromJson(rs.getString("value"), LocalDate.class);
                    if (lastTaxes.plusDays(3).isBefore(LocalDate.now())) {
                        SQLManager.getInstance().update("UPDATE GLOBALDATAS SET value='" + JedisManager.getGson().toJson(LocalDate.now()) + "' WHERE data='lastTaxes'");
                        launchTaxes();
                    }
                } else {
                    SQLManager.getInstance().update("INSERT INTO GLOBALDATAS(data, value) VALUES ('lastTaxes', '" + JedisManager.getGson().toJson(LocalDate.now()) + "')");
                    launchTaxes();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private static void launchTaxes() {
        for (UserAccount userAccount : UserAccount.getAllAccounts()) {
            BankAccount bankAccount = userAccount.getBankAccount();

            System.out.println(prefix + "Taxing " + bankAccount.getAmount() * 0.04 + " to account " + bankAccount.getID());
            bankAccount.setAmount(bankAccount.getAmount() * 0.96);
        }
    }
}
