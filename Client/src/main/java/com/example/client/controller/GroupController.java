package com.example.client.controller;

import com.example.client.Main;
import com.example.client.RestClient;
import com.example.client.model.Group;
import com.fasterxml.jackson.core.type.TypeReference;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class GroupController {
    @FXML
    MenuItem main;
    @FXML
    TableView<Group> table;
    @FXML
    Button addButton;

    ObservableList<Group> groups;
    FXMLLoader loader;

    @FXML
    public void initialize() {
        main.setOnAction(event -> openMain());
        addButton.setOnAction(event -> addDialog());
        setTable();
    }

    private void addDialog() {
        loader = new FXMLLoader(Main.class.getResource("dialog-view.fxml"));
        try {
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            DialogController dialogController = loader.getController();
            TextField title = new TextField();
            TextField speciality = new TextField();
            title.setPromptText("Название");
            speciality.setPromptText("Специальность");

            dialogController.addInput(title, speciality);
            dialogController.setTitle("Добавление группы");
            Button applyButton = dialogController.getApplyButton();
            applyButton.setOnAction(event -> addGroup(title, speciality));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addGroup(TextField title, TextField speciality) {
        try {
            System.out.println("TEWST");
            Group group = new Group(title.getText(), speciality.getText());
            RestClient.add("/groups/add", group);
            setTable();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setTable() {
        Platform.runLater(() -> {
            try {
                groups = FXCollections.observableArrayList(getGroups());
                TableColumn<Group, String> title = new TableColumn<>("Название");
                title.setCellValueFactory(new PropertyValueFactory<>("title"));

                TableColumn<Group, String> speciality = new TableColumn<>("Специальность");
                speciality.setCellValueFactory(new PropertyValueFactory<>("speciality"));

                table.setItems(groups);
                table.getColumns().setAll(title, speciality);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private List<Group> getGroups() throws IOException {
        return RestClient.get("/groups", new TypeReference<>() {
        });
    }

    private void openMain() {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        try {
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) main.getParentPopup().getOwnerWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
