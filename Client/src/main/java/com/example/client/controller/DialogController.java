package com.example.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Getter;

@Getter
public class DialogController {
    @FXML
    private Label title;
    @FXML
    private VBox inputs;

    @FXML
    private Button applyButton;
    @FXML
    private Button closeButton;

    @FXML
    public void initialize() {
        closeButton.setOnAction(event -> {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });
    }

    public void addInput(TextField... textFields) {
        inputs.getChildren().addAll(textFields);
    }

    public void setTitle(String title){
        this.title.setText(title);
    }
}
