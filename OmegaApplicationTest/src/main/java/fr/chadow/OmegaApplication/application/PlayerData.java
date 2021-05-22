package fr.chadow.OmegaApplication.application;

import fr.ChadOW.api.accounts.UserAccount;
import javafx.scene.layout.GridPane;

import java.util.UUID;

public class PlayerData extends GridPane {

    public PlayerData(UUID uuid) {
        UserAccount userAccount = UserAccount.getAccount(uuid);


    }
}
