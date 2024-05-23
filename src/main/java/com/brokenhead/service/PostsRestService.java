package com.brokenhead.service;

import com.brokenhead.model.response.PostResponse;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class PostsRestService implements RestService<PostResponse> {

    private static final String POSTS_URL = "/posts";
    private static final String FULL_URL = BASE_SERVICE_URL + POSTS_URL;

    @Override
    public List<PostResponse> getAllEntities() {
        return given()
                .contentType(ContentType.JSON)
                .log().method()
                .log().uri()
                .when()
                .get(FULL_URL)
                .then()
                .assertThat().statusCode(200)
                .log().body()
                .extract()
                .as(new TypeRef<>() {
                });
    }

    @Override
    public List<PostResponse> getListOfEntities(Map<String, String> queryParams) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .queryParams(queryParams)
                .when()
                .get(FULL_URL)
                .then()
                .assertThat().statusCode(200)
                .log().body()
                .extract()
                .as(new TypeRef<>() {
                });
    }

    @Override
    public PostResponse getSingleEntity(String entityId) {
        return given()
                .contentType(ContentType.JSON)
                .log().method()
                .log().uri()
                .when()
                .get(FULL_URL + "/" + entityId)
                .then()
                .assertThat().statusCode(200)
                .log().body()
                .extract()
                .as(PostResponse.class);
    }

    @Override
    public PostResponse createEntity(String requestBody) {
        return given()
                .body(requestBody)
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .post(FULL_URL)
                .then()
                .assertThat().statusCode(201)
                .log().status()
                .log().body()
                .extract()
                .as(PostResponse.class);
    }

    @Override
    public PostResponse updateEntity(String entityId, String requestBody) {
        return given()
                .body(requestBody)
                .contentType(ContentType.JSON)
                .when()
                .log().all()
                .put(FULL_URL + "/" + entityId)
                .as(PostResponse.class);
    }

    @Override
    public PostResponse patchEntity(String entityId, String requestBody) {
        return given()
                .body(requestBody)
                .contentType(ContentType.JSON)
                .when()
                .log().all()
                .patch(FULL_URL + "/" + entityId)
                .as(PostResponse.class);
    }

    @Override
    public Response deleteEntity(String entityId) {
        return given()
                .log().method()
                .log().uri()
                .contentType(ContentType.JSON)
                .when()
                .delete(FULL_URL + "/" + entityId)
                .andReturn();
    }
}

/*

Allure.addAttachment("Put Request Details", "application/json", ofNullable(payload).map(Object::toString).orElse
(EMPTY), "json");
Allure.addAttachment("GET Response Details for endpoint: " + baseURI + specificResourceUri, "application/json",
response.asString(), "json");

 */