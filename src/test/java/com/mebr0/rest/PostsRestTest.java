package com.mebr0.rest;

import com.mebr0.dto.Post;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class PostsRestTest {

    @Test
    public void testListPosts() {
        var posts = given().
                when().
                get("/posts").
                then().
                statusCode(200).
                extract().body().as(Post[].class);

        assertNotNull(posts);
        assertTrue(posts.length > 0);

        Arrays.stream(posts).forEach(post -> {
            assertNotNull(post.getId());
            assertNotNull(post.getTitle());
            assertNotNull(post.getBody());
            assertNotNull(post.getUserId());
        });
    }

    @Test
    public void testCreatePost() {
        var postToCreate = new Post(null, "title", "body", null);

        var createdPost = given().
                when().
                body(postToCreate).
                header("Content-Type", "application/json").
                post("/posts").
                then().
                statusCode(201).
                extract().body().as(Post.class);

        assertNotNull(createdPost);
        assertNotNull(createdPost.getId());
        assertEquals(postToCreate.getTitle(), createdPost.getTitle());
        assertEquals(postToCreate.getBody(), createdPost.getBody());
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 3, 4, 50})
    public void testGetPost(long id) {
        var post = given().
                when().
                pathParam("id", id).
                get("/posts/{id}").
                then().
                statusCode(200).
                extract().body().as(Post.class);

        assertNotNull(post);
        assertNotNull(post.getId());
        assertNotNull(post.getTitle());
        assertNotNull(post.getBody());
        assertNotNull(post.getUserId());
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 3, 4, 50})
    public void testUpdatePost(long id) {
        var postToUpdate = new Post(null, "title", "body", null);

        var updatedPost = given().
                when().
                pathParam("id", id).
                body(postToUpdate).
                header("Content-Type", "application/json").
                put("/posts/{id}").
                then().
                statusCode(200).
                extract().body().as(Post.class);

        assertNotNull(updatedPost);
        assertEquals(id, updatedPost.getId());
        assertEquals(updatedPost.getTitle(), updatedPost.getTitle());
        assertEquals(updatedPost.getBody(), updatedPost.getBody());
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 3, 4, 50})
    public void testDeletePost(long id) {
        given().
                when().
                pathParam("id", id).
                delete("/posts/{id}").
                then().
                statusCode(204);
    }
}
