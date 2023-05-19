package com.example.client.controller;

import com.example.client.Main;
import com.example.client.RestClient;
import com.example.client.model.Group;
import com.example.client.model.Subject;
import com.example.client.model.Teacher;
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

public class SubjectController {
    @FXML
    MenuItem main;
    @FXML
    TableView<Subject> table;
    @FXML
    Button addButton;
    @FXML
    Button updateButton;
    @FXML
    Button deleteButton;
    ObservableList<Subject> subjects;
    FXMLLoader loader;

    @FXML
    public void initialize() {
        main.setOnAction(event -> openMain());
        addButton.setOnAction(event -> addDialog());
        updateButton.setOnAction(event -> updateDialog());
        deleteButton.setOnAction(event -> deleteSubject());
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

            title.setPromptText("Название");

            DialogController dialogController = setDialogController(loader, "Добавление предмета", title);

            Button applyButton = dialogController.getApplyButton();
            applyButton.setOnAction(event -> addSubject(title));

            setDialog(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateDialog() {
        loader = new FXMLLoader(Main.class.getResource("dialog-view.fxml"));
        Subject subject = table.getSelectionModel().getSelectedItem();
        try {
            Scene scene = new Scene(loader.load());

            TextField title = new TextField();

            title.setPromptText("Название");
            title.setText(subject.getTitle());

            DialogController dialogController = setDialogController(loader, "Обновление предмета", title);

            Button applyButton = dialogController.getApplyButton();

            applyButton.setOnAction(event -> updateSubject(title, subject.getId()));

            setDialog(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Subject> getSubjects() throws IOException {
        return RestClient.get("/subjects", new TypeReference<>() {
        });
    }

    private void addSubject(TextField title) {
        Subject subject = new Subject(title.getText());
        new Thread(() -> {
            try {
                RestClient.add("/subjects/add", subject);
                setTable();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void updateSubject(TextField title, Long id) {
        Subject subject = new Subject(title.getText());
        new Thread(() -> {
            try {
                RestClient.update("/subjects/" + id, subject);
                setTable();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }).start();
    }

    private void deleteSubject() {
        Subject subject = table.getSelectionModel().getSelectedItem();
        new Thread(() -> {
            try {
                RestClient.delete("/subjects/" + subject.getId());
                setTable();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void setTable() {
        Platform.runLater(() -> {
            try {
                subjects = FXCollections.observableArrayList(getSubjects());
                TableColumn<Subject, String> title = new TableColumn<>("Название");
                title.setCellValueFactory(new PropertyValueFactory<>("title"));

                table.setItems(subjects);
                table.getColumns().setAll(title);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
