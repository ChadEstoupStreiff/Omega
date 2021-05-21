package fr.chadow.OmegaApplication.application;

import fr.chadow.OmegaApplication.AppMain;
import fr.chadow.OmegaApplication.utils.JedisManager;
import fr.chadow.OmegaApplication.utils.SQLManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class ConnectionControl extends GridPane {

    private static ConnectionControl INSTANCE;

    @FXML
    private TextField sqlHost;
    @FXML
    private TextField sqlPort;
    @FXML
    private TextField sqlUser;
    @FXML
    private TextField sqlDatabase;
    @FXML
    private TextField redisHost;
    @FXML
    private TextField redisPort;
    @FXML
    private PasswordField sqlPassword;
    @FXML
    private PasswordField redisPassword;

    public ConnectionControl() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ConnectView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        INSTANCE = this;
    }

    @FXML
    public void close() {
        OmegaApplication.getInstance().setLeft(null);
    }

    @FXML
    public void connectClick() {
        OmegaApplication.getInstance().setLeft(null);
        Logger.print("Connection ...");
        Platform.runLater(() -> {
            new SQLManager(sqlHost.getText(), Integer.parseInt(sqlPort.getText()), sqlDatabase.getText(), sqlUser.getText(), sqlPassword.getText());
            new JedisManager(redisHost.getText(), Integer.parseInt(redisPort.getText()), redisPassword.getText());
            Logger.print("Connected.");
        });
    }

    public static Node getInstance() {
        return INSTANCE;
    }
}