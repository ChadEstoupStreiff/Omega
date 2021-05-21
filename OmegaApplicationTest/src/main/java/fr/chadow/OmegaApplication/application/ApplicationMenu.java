package fr.chadow.OmegaApplication.application;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;

public class ApplicationMenu extends MenuBar {

    public ApplicationMenu() {
        initMenu();
        initDatabase();
    }

    private void initDatabase() {

        Menu menuP = new Menu("DataBase");
        getMenus().add(menuP);

        MenuItem menu = new MenuItem("Connection");
        menu.setOnAction(actionEvent -> OmegaApplication.getInstance().setLeft(ConnectionControl.getInstance()));
        menuP.getItems().add(menu);

        menu = new MenuItem("DataBase");
        menu.setOnAction(actionEvent -> {
            ScrollPane scrollPane = new ScrollPane();
            FlowPane flowPane = new FlowPane();
            flowPane.getChildren().add(new DataBaseReader());
            scrollPane.setContent(flowPane);
            scrollPane.setPannable(true);
            OmegaApplication.getInstance().setCenter(scrollPane);
        });
        menuP.getItems().add(menu);
    }

    private void initMenu() {

        Menu menuP = new Menu("Menu");
        getMenus().add(menuP);

        MenuItem menu = new Menu("Players datas");
        menu.setOnAction(actionEvent -> {
            //TODO SHOW PLAYERS DATAS
            Logger.print("WOW !");
        });
        menuP.getItems().add(menu);

        menu = new Menu("Online Players");
        menu.setOnAction(actionEvent -> {
            //TODO SHOW PLAYERS DATAS
            Logger.print("WOW !");
        });
        menuP.getItems().add(menu);

        menu = new Menu("Servers");
        menu.setOnAction(actionEvent -> {
            //TODO SHOW PLAYERS DATAS
            Logger.print("WOW !");
        });
        menuP.getItems().add(menu);

        menu = new Menu("Administration");
        menu.setOnAction(actionEvent -> {
            //TODO SHOW PLAYERS DATAS
            Logger.print("WOW !");
        });
        menuP.getItems().add(menu);

        menu = new Menu("Players datas");
        menu.setOnAction(actionEvent -> {
            //TODO SHOW PLAYERS DATAS
            Logger.print("WOW !");
        });
        menuP.getItems().add(menu);
    }
}
