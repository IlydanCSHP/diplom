package com.example.client;

import com.example.client.model.Group;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class RestClient {
    private static final String API_URL = "http://localhost:8080/api/v1";
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final HttpClient client = HttpClientBuilder.create().build();


    public static <T> T get(String endpoint, TypeReference<T> responseClass) throws IOException {
        URL url = new URL(API_URL + endpoint);
        HttpGet request = new HttpGet(url.toString());
        HttpResponse response = client.execute(request);
        String json = EntityUtils.toString(response.getEntity());

        return mapper.readValue(json, responseClass);
    }

    public static <T> void add(String endpoint, T addedClass) throws IOException {
        URL url = new URL(API_URL + endpoint);
        String requestBody = mapper.writeValueAsString(addedClass);

        HttpPost request = new HttpPost(url.toString());
        request.setEntity(new StringEntity(requestBody));
        request.setHeader("Content-Type", "application/json");

        client.execute(request);
    }

    public static void delete(String endpoint) throws IOException {
        URL url = new URL(API_URL + endpoint);
        HttpDelete request = new HttpDelete(url.toString());

        client.execute(request);
    }


}
