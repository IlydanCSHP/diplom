package com.example.client;

import com.example.client.model.Employee;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.List;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        try {
            System.out.println(RestAPI.get("/employees"));
            List<Employee> employees = RestAPI.get("/employees");
            if (!employees.isEmpty()) {
                Employee employee = employees.get(0);
                RestAPI.add(employee);
            }
            System.out.println(RestAPI.get("/employees"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}