package com.example.client.controller;

import com.example.client.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    MenuItem groups;
    FXMLLoader loader;

    @FXML
    public void initialize() {
        groups.setOnAction(event -> openDirectory(new GroupController()));
    }

    private <T> void openDirectory(T controller) {
        loader = new FXMLLoader(Main.class.getResource("groups-view.fxml"));
        loader.setControllerFactory(aClass -> controller);
        try {
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) groups.getParentPopup().getOwnerWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
