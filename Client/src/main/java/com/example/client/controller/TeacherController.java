package com.example.client.controller;

import com.example.client.Main;
import com.example.client.RestClient;
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

public class TeacherController {
    @FXML
    MenuItem main;
    @FXML
    Button addButton;
    @FXML
    Button updateButton;
    @FXML
    Button deleteButton;
    @FXML
    TableView<Teacher> table;
    @FXML
    ObservableList<Teacher> teachers;
    FXMLLoader loader;

    @FXML
    public void initialize() {
        main.setOnAction(event -> openMain());
        addButton.setOnAction(event -> addDialog());
        updateButton.setOnAction(event -> updateDialog());
        deleteButton.setOnAction(event -> deleteTeacher());
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
            TextField name = new TextField();
            TextField secondName = new TextField();
            TextField lastName = new TextField();
            TextField commission = new TextField();

            name.setPromptText("Имя");
            secondName.setPromptText("Фамилия");
            lastName.setPromptText("Отчество");
            commission.setPromptText("Коммисия");

            DialogController dialogController = setDialogController(loader, "Добавление преподавателя", name, secondName, lastName, commission);

            Button applyButton = dialogController.getApplyButton();
            applyButton.setOnAction(event -> addTeacher(name, secondName, lastName, commission));

            setDialog(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateDialog() {
        loader = new FXMLLoader(Main.class.getResource("dialog-view.fxml"));
        Teacher teacher = table.getSelectionModel().getSelectedItem();
        try {
            Scene scene = new Scene(loader.load());

            TextField name = new TextField();
            TextField secondName = new TextField();
            TextField lastName = new TextField();
            TextField commission = new TextField();

            name.setPromptText("Имя");
            name.setText(teacher.getName());

            secondName.setPromptText("Фамилия");
            secondName.setText(teacher.getSecondName());

            lastName.setPromptText("Отчество");
            lastName.setText(teacher.getLastName());

            commission.setPromptText("Коммисия");
            commission.setText(teacher.getCommission());

            DialogController dialogController = setDialogController(loader, "Обновление преподавателя", name, secondName, lastName, commission);

            Button applyButton = dialogController.getApplyButton();
            applyButton.setOnAction(event -> updateTeacher(name, secondName, lastName, commission, teacher.getId()));

            setDialog(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Teacher> getTeachers() throws IOException {
        return RestClient.get("/teachers", new TypeReference<>() {
        });
    }

    private void addTeacher(TextField name, TextField secondName, TextField lastName, TextField commission) {
        new Thread(() -> {
            Teacher teacher = new Teacher(name.getText(), secondName.getText(), lastName.getText(), commission.getText());
            try {
                RestClient.add("/teachers/add", teacher);
                setTable();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void updateTeacher(TextField name, TextField secondName, TextField lastName, TextField commission, Long id) {
        Teacher teacher = new Teacher(name.getText(), secondName.getText(), lastName.getText(), commission.getText());
        new Thread(() -> {
            try {
                RestClient.update("/teachers/" + id, teacher);
                setTable();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void deleteTeacher() {
        new Thread(() -> {
            Teacher teacher = table.getSelectionModel().getSelectedItem();
            try {
                RestClient.delete("/teachers/" + teacher.getId());
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
                teachers = FXCollections.observableArrayList(getTeachers());
                TableColumn<Teacher, String> name = new TableColumn<>("Имя");
                name.setCellValueFactory(new PropertyValueFactory<>("name"));

                TableColumn<Teacher, String> secondName = new TableColumn<>("Фамилия");
                secondName.setCellValueFactory(new PropertyValueFactory<>("secondName"));

                TableColumn<Teacher, String> lastName = new TableColumn<>("Отчество");
                lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

                TableColumn<Teacher, String> commission = new TableColumn<>("ЦК");
                commission.setCellValueFactory(new PropertyValueFactory<>("commission"));


                table.setItems(teachers);
                table.getColumns().setAll(name, secondName, lastName, commission);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
