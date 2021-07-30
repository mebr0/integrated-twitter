package com.mebr0.rest;

import com.mebr0.dto.Comment;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class CommentsRestTest {

    private final Long postId = 1L;

    @Test
    public void testListComments() {
        given().
            when().
                pathParam("id", postId).
                get("/posts/{id}/comments").
            then().
                statusCode(200).
                body("comments", notNullValue()).
                body("comments.size()", greaterThan(0)).
                body("comments.comment", notNullValue()).
                body("comments.comment.id", notNullValue()).
                body("comments.comment.name", notNullValue()).
                body("comments.comment.body", notNullValue()).
                body("comments.comment.email", notNullValue()).
                body("comments.comment.postId", notNullValue());
    }

    @Test
    public void testCreateComment() {
        var commentToCreate = new Comment(null, "name", "body", "email", null);

        given().
            when().
                pathParam("id", postId).
                contentType(ContentType.XML).
                body(commentToCreate).
                post("/posts/{id}/comments").
            then().
                statusCode(201).
                body("comment", notNullValue()).
                body("comment.id", notNullValue()).
                body("comment.name", equalTo(commentToCreate.getName())).
                body("comment.body", equalTo(commentToCreate.getBody())).
                body("comment.email", equalTo(commentToCreate.getEmail())).
                body("comment.postId", equalTo(postId.toString()));
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 3, 4, 50})
    public void testGetComment(Long id) {
        given().
            when().
                pathParam("postId", postId).
                pathParam("id", id).
                get("/posts/{postId}/comments/{id}").
            then().
                statusCode(200).
                body("comment", notNullValue()).
                body("comment.id", equalTo(id.toString())).
                body("comment.name", notNullValue()).
                body("comment.body", notNullValue()).
                body("comment.email", notNullValue());
//                body("comment.postId", equalTo(postId.toString()));
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 3, 4, 50})
    public void testUpdateComment(Long id) {
        var commentToUpdate = new Comment(null, "name", "body", "email", null);

        given().
            when().
                pathParam("postId", postId).
                pathParam("id", id).
                contentType(ContentType.XML).
                body(commentToUpdate).
                put("/posts/{postId}/comments/{id}").
            then().
                statusCode(200).
                body("comment", notNullValue()).
                body("comment.id", notNullValue()).
                body("comment.name", equalTo(commentToUpdate.getName())).
                body("comment.body", equalTo(commentToUpdate.getBody())).
                body("comment.email", equalTo(commentToUpdate.getEmail()));
//                body("comment.postId", equalTo(postId.toString()));
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 3, 4, 50})
    public void testDeleteComment(Long id) {
        given().
            when().
                pathParam("postId", postId).
                pathParam("id", id).
                delete("/posts/{postId}/comments/{id}").
            then().
                statusCode(204);
    }
}
