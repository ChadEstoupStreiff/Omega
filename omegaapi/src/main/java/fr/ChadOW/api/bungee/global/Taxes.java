package fr.ChadOW.api.bungee.global;

import fr.ChadOW.api.accounts.BankAccount;
import fr.ChadOW.api.accounts.UserAccount;
import fr.ChadOW.api.managers.JedisManager;
import fr.ChadOW.api.managers.SQLManager;

import java.sql.SQLException;
import java.time.LocalDate;

public class Taxes {
    private static final String prefix = "[Taxes] ";

    private static LocalDate lastTaxes;

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
