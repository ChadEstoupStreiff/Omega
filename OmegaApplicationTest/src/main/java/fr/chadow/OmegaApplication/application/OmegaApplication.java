package fr.chadow.OmegaApplication.application;

import javafx.scene.layout.BorderPane;

public class OmegaApplication extends BorderPane {
    private static OmegaApplication INSTANCE;

    public OmegaApplication() {
        setBottom(new ApplicationMenu());
        setLeft(new ConnectionControl());
        setRight(new Logger());

        INSTANCE = this;
    }

    public static OmegaApplication getInstance() {
        return INSTANCE;
    }
}
