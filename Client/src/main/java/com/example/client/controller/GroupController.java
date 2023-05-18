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
    @FXML
    Button updateButton;
    @FXML
    Button deleteButton;
    ObservableList<Group> groups;
    FXMLLoader loader;

    @FXML
    public void initialize() {
        main.setOnAction(event -> openMain());
        addButton.setOnAction(event -> addDialog());
        updateButton.setOnAction(event -> updateDialog());
        deleteButton.setOnAction(event -> deleteGroup());
        setTable();
    }

    private DialogController setDialogController(FXMLLoader loader, String title, TextField... textFields) throws IOException {
        DialogController dialogController = loader.getController();
        dialogController.addInput(textFields);
        dialogController.setTitle(title);

        return loader.getController();
    }

    private void setDialog(Scene scene) throws IOException {
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void addDialog() {
        loader = new FXMLLoader(Main.class.getResource("dialog-view.fxml"));
        try {
            Scene scene = new Scene(loader.load());
            TextField title = new TextField();
            TextField speciality = new TextField();

            title.setPromptText("Название");
            speciality.setPromptText("Специальность");

            DialogController dialogController = setDialogController(loader, "Добавление группы", title, speciality);

            Button applyButton = dialogController.getApplyButton();
            applyButton.setOnAction(event -> addGroup(title, speciality));

            setDialog(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateDialog() {
        loader = new FXMLLoader(Main.class.getResource("dialog-view.fxml"));
        Group group = table.getSelectionModel().getSelectedItem();
        try {
            Scene scene = new Scene(loader.load());
            TextField title = new TextField();
            TextField speciality = new TextField();

            title.setPromptText("Название");
            title.setText(group.getTitle());

            speciality.setPromptText("Специальность");
            speciality.setText(group.getSpeciality());

            DialogController dialogController = setDialogController(loader, "Обновление группы", title, speciality);

            Button applyButton = dialogController.getApplyButton();
            applyButton.setOnAction(event -> updateGroup(title, speciality, group.getId()));

            setDialog(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addGroup(TextField title, TextField speciality) {
        new Thread(() -> {
            try {
                Group group = new Group(title.getText(), speciality.getText());
                RestClient.add("/groups/add", group);
                setTable();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void updateGroup(TextField title, TextField speciality, Long id) {
        new Thread(() -> {
            try {
                Group group = new Group(title.getText(), speciality.getText());
                RestClient.update("/groups/" + id, group);
                setTable();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void deleteGroup() {
        new Thread(() -> {
            try {
                Group group = table.getSelectionModel().getSelectedItem();
                RestClient.delete("/groups/" + group.getId());
                setTable();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
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
