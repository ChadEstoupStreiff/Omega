package fr.ChadOW.api.managers;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OmegaAPIUtils {

    static {
        getData();
    }

    public static void getData() {
        userNames = new HashMap<>();
        SQLManager.getInstance().query("SELECT uuid, lastName FROM DATAS", rs -> {
            try {
                while (rs.next()) {
                    userNames.put(UUID.fromString(rs.getString("uuid")), rs.getString("lastName"));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        bankNames = new HashMap<>();
        SQLManager.getInstance().query("SELECT bankID, name FROM BANKS", rs -> {
            try {
                while (rs.next()) {
                    bankNames.put(rs.getString("bankID"), rs.getString("name"));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        groupNames = new HashMap<>();
        SQLManager.getInstance().query("SELECT groupID, name FROM GROUPS", rs -> {
            try {
                while (rs.next()) {
                    groupNames.put(rs.getString("groupID"), rs.getString("name"));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        System.out.println("Data updated.");
    }

    private static Map<UUID, String> userNames;
    private static Map<String, String> bankNames;
    private static Map<String, String> groupNames;


    public static double roundDouble(double a) {
        return (int)(a*100)/100d;
    }

    public static String tryToConvertIDToString(String id) {
        String str = tryToConvertIDToStringByUserAccount(id);
        if (str.equals(id)) {
            str = tryToConvertIDToStringByBankAccount(id);
            if (str.equals(id))
                str = tryToConvertIDToStringByGroupAccount(id);
        }
        return str;
    }

    public static String tryToConvertIDToStringByUserAccount(String id) {
        return userNames.getOrDefault(UUID.fromString(id), id);
    }

    public static String tryToConvertIDToStringByBankAccount(String id) {
        return bankNames.getOrDefault(id, id);
    }

    public static String tryToConvertIDToStringByGroupAccount(String id) {
        return groupNames.getOrDefault(id, id);
    }
}
