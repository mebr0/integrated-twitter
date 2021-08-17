package com.mebr0.rest;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
class UsersRestTest {

    @Test
    void testListPosts() {
        given().
            when().
                get("/users").
            then().
                statusCode(200).
                body("$", notNullValue()).
                body("$.size()", greaterThan(0)).
                body("$.id", notNullValue()).
                body("$.name", notNullValue()).
                body("$.username", notNullValue()).
                body("$.email", notNullValue()).
                body("$.phone", notNullValue());
    }
}
