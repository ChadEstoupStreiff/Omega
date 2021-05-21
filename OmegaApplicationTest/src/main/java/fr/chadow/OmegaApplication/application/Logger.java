package fr.chadow.OmegaApplication.application;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger extends ScrollPane {

    private static Logger INSTANCE;

    private final Label label;
    private int messageCounter;

    public Logger() {
        INSTANCE = this;
        messageCounter = 0;

        FlowPane flowPane = new FlowPane();
        label = new Label();
        flowPane.setAlignment(Pos.BASELINE_LEFT);
        setContent(flowPane);
        setPannable(true);
        flowPane.getChildren().add(label);
    }

    public static Logger getINSTANCE() {
        return INSTANCE;
    }

    public static void print(String message) {
        getINSTANCE().send(message);
    }
    private void send(String message) {
        messageCounter++;
        label.setText(
                "[#" + messageCounter + "] [" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "] " + message
                + '\n' + label.getText());
    }
}
