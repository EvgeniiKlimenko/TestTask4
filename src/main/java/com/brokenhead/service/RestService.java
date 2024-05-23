package com.brokenhead.service;

import io.restassured.response.Response;

import java.util.List;
import java.util.Map;


public interface RestService<T> {

    String BASE_SERVICE_URL = "https://jsonplaceholder.typicode.com";

    List<T> getAllEntities();
    List<T> getListOfEntities(Map<String, String> queryParams);
    T getSingleEntity(String entityId);
    T createEntity(String requestBody);
    T updateEntity(String entityId, String requestBody);
    T patchEntity(String entityId, String requestBody);
    Response deleteEntity(String entityId);
}
