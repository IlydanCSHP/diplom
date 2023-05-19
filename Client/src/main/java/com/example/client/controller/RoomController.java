package com.example.client.controller;

import com.example.client.Main;
import com.example.client.RestClient;
import com.example.client.model.Room;
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

public class RoomController {
    @FXML
    MenuItem main;
    @FXML
    TableView<Room> table;
    @FXML
    Button addButton;
    @FXML
    Button updateButton;
    @FXML
    Button deleteButton;
    ObservableList<Room> rooms;
    FXMLLoader loader;

    @FXML
    public void initialize() {
        main.setOnAction(event -> openMain());
        addButton.setOnAction(event -> addDialog());
        updateButton.setOnAction(event -> updateDialog());
        deleteButton.setOnAction(event -> deleteRoom());
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
            TextField number = new TextField();

            number.setPromptText("Номер");

            DialogController dialogController = setDialogController(loader, "Добавление аудитории", number);

            Button applyButton = dialogController.getApplyButton();
            applyButton.setOnAction(event -> addRoom(number));

            setDialog(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateDialog() {
        loader = new FXMLLoader(Main.class.getResource("dialog-view.fxml"));
        Room room = table.getSelectionModel().getSelectedItem();
        try {
            Scene scene = new Scene(loader.load());
            TextField number = new TextField();

            number.setPromptText("Номер");
            number.setText(String.valueOf(room.getNumber()));

            DialogController dialogController = setDialogController(loader, "Обновление аудитории", number);

            Button applyButton = dialogController.getApplyButton();
            applyButton.setOnAction(event -> updateRoom(number, room.getId()));

            setDialog(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Room> getRooms() throws IOException {
        return RestClient.get("/rooms", new TypeReference<>() {
        });
    }

    private void addRoom(TextField number) {
        new Thread(() -> {
            try {
                Room room = new Room(Integer.parseInt(number.getText()));
                RestClient.add("/rooms/add", room);
                setTable();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void updateRoom(TextField number, Long id) {
        new Thread(() -> {
            try {
                Room room = new Room(Integer.parseInt(number.getText()));
                RestClient.update("/rooms/" + id, room);
                setTable();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void deleteRoom() {
        new Thread(() -> {
            try {
                Room room = table.getSelectionModel().getSelectedItem();
                RestClient.delete("/rooms/" + room.getId());
                setTable();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
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

    private void setTable() {
        Platform.runLater(() -> {
            try {
                rooms = FXCollections.observableArrayList(getRooms());
                TableColumn<Room, Integer> number = new TableColumn<>("Номер");
                number.setCellValueFactory(new PropertyValueFactory<>("number"));

                table.setItems(rooms);
                table.getColumns().setAll(number);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
