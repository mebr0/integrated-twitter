package com.mebr0.rest;

import com.mebr0.dto.Post;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class PostsRestTest {

    @Test
    void testListPosts() {
        given().
            when().
                get("/posts").
            then().
                statusCode(200).
                body("$", notNullValue()).
                body("$.size()", greaterThan(0)).
                body("$.id", notNullValue()).
                body("$.title", notNullValue()).
                body("$.body", notNullValue()).
                body("$userId", notNullValue());
    }

    @Test
    void testCreatePost() {
        var postToCreate = new Post(null, "title", "body", null);

        given().
            when().
                contentType(ContentType.JSON).
                body(postToCreate).
                post("/posts").
            then().
                statusCode(201).
                body("$", notNullValue()).
                body("id", notNullValue()).
                body("title", equalTo(postToCreate.getTitle())).
                body("body", equalTo(postToCreate.getBody()));
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 3, 4, 50})
    void testGetPost(long id) {
        given().
            when().
                pathParam("id", id).
                get("/posts/{id}").
            then().
                statusCode(200).
                body("$", notNullValue()).
                body("id", notNullValue()).
                body("title", notNullValue()).
                body("body", notNullValue()).
                body("userId", notNullValue());
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 3, 4, 50})
    void testUpdatePost(long id) {
        var postToUpdate = new Post(null, "title", "body", null);

        given().
            when().
                pathParam("id", id).
                contentType(ContentType.JSON).
                body(postToUpdate).
                put("/posts/{id}").
            then().
                statusCode(200).
                body("$", notNullValue()).
                body("id", notNullValue()).
                body("title", equalTo(postToUpdate.getTitle())).
                body("body", equalTo(postToUpdate.getBody()));
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 3, 4, 50})
    void testDeletePost(long id) {
        given().
            when().
                pathParam("id", id).
                delete("/posts/{id}").
            then().
                statusCode(204);
    }
}
