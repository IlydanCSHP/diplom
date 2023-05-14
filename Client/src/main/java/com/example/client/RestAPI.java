package com.example.client;

import com.example.client.model.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class RestAPI {
    private static final String API_URL = "http://localhost:8080/api/v1";

    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<Employee> get(String resource) throws IOException {
        URL url = new URL(API_URL + resource);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
        );
        String response = reader.readLine();

        Employee[] employees = mapper.readValue(response, Employee[].class);

        return Arrays.asList(employees);
    }

    public static void delete(Long id) throws IOException {
        URL url = new URL(API_URL + "/employees?id=" + id);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");
        int responseCode = connection.getResponseCode();
        connection.disconnect();
        if (responseCode != HttpURLConnection.HTTP_NO_CONTENT) {
            throw new RuntimeException("Failed to delete employee with id " + id);
        }
    }

    public static void add(Employee employee) throws IOException{
        URL url = new URL(API_URL + "/employees/add");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");

        String employeeJson = mapper.writeValueAsString(employee);
        System.out.println(employeeJson);
        connection.setDoOutput(true);
        try (OutputStream stream = connection.getOutputStream()){
            byte[] input = employeeJson.getBytes();
            stream.write(input, 0, input.length);
        }
        int responseCode = connection.getResponseCode();
        connection.disconnect();
        if (responseCode != HttpURLConnection.HTTP_CREATED) {
            throw new RuntimeException("Failed to add new employee");
        }
    }
}
