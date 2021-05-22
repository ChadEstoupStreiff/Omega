package fr.chadow.OmegaApplication.application;

import fr.ChadOW.api.managers.SQLManager;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class DataBaseReader extends GridPane {

    private static final Font columnFont = Font.font("Courier", FontWeight.BOLD, 20);
    private static final Font dataFont = Font.font("Courier", FontWeight.NORMAL, 12);

    private static final List<String> columns = Arrays.asList("u.uuid", "d.lastName", "d.lastIP", "u.rank", "b.bankID", "b.amount", "j.jobID", "j.type", "j.level", "j.exp", "g.groupID", "g.name", "g.chef");

    public DataBaseReader() {
        setGridLinesVisible(true);
        update();
    }

    private void update() {
        Logger.print("Getting datas ...");
        SQLManager.getInstance().query("SELECT *\n" +
                        "FROM USERS u\n" +
                        "JOIN DATAS d ON u.uuid = d.uuid\n" +
                        "JOIN BANKS b ON u.bankID = b.bankID\n" +
                        "LEFT JOIN JOBS j ON u.jobID = j.jobID\n" +
                        "LEFT JOIN GROUPS g ON u.groupID = g.groupID",
                resultSet -> {
                    try {
                        int size = columns.size();
                        for (int i = 0; i < size; i++){
                            Label label = new Label(columns.get(i));
                            label.setFont(columnFont);
                            label.setPadding(new Insets(5));
                            add(label, i, 0);
                        }

                        int index = 1;
                        while (resultSet.next()) {
                            for (int i = 0; i < size; i++){
                                Label label = new Label(resultSet.getString(columns.get(i)));
                                label.setFont(dataFont);
                                label.setPadding(new Insets(5));
                                add(label, i, index);
                            }
                            index++;
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                });
        Logger.print("Datas got.");
    }
}
