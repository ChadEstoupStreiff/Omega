package fr.chadow.OmegaApplication;

import fr.chadow.OmegaApplication.application.OmegaApplication;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class AppMain extends javafx.application.Application {
    public static void main(String[] args) {
        javafx.application.Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setScene(new Scene(new OmegaApplication()));
        stage.setTitle("OmegaApplication");
        stage.setMaximized(true);
        stage.show();
    }
}
