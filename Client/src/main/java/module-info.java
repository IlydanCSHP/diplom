module com.example.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires lombok;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;


    opens com.example.client to javafx.fxml;
    opens com.example.client.controller to javafx.fxml;
    exports com.example.client;
    exports com.example.client.model;
    exports com.example.client.controller;
}