package com.mebr0.rest;

import com.mebr0.dto.Post;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestParamType;

public class PostsRest extends RouteBuilder {

    @Override
    public void configure() {
        rest().
            produces("application/json").

            get("/posts").
                id("list-posts").
                description("List all posts").
                responseMessage().
                    code(200).message("Operation finished successfully").responseModel(Post[].class).
                endResponseMessage().

                route().
                to("direct:service-list_posts").
                marshal().json().
                endRest().

            post("/posts").
                id("create-post").
                description("Create new post").
                param().name("body").type(RestParamType.body).description("Body of post").dataType("Post").endParam().
                responseMessage().
                    code(201).message("Operation finished successfully").responseModel(Post.class).
                endResponseMessage().
                consumes("application/json").

                route().
                to("direct:service-create_post").
                marshal().json().
                setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201)).
                endRest().

            get("/posts/{id}").
                id("retrieve-post").
                description("Get post by id").
                param().name("id").type(RestParamType.path).description("Unique id of post").dataType("integer").endParam().
                responseMessage().
                    code(200).message("Operation finished successfully").responseModel(Post.class).
                endResponseMessage().

                route().
                to("direct:service-get_post").
                marshal().json().
                endRest().

            put("/posts/{id}").
                id("update-post").
                description("Update post by id").
                param().name("id").type(RestParamType.path).description("Unique id of post").dataType("integer").endParam().
                param().name("body").type(RestParamType.body).description("Body of post").dataType("Post").endParam().
                responseMessage().
                    code(200).message("Operation finished successfully").responseModel(Post.class).
                endResponseMessage().
                consumes("application/json").

                route().
                to("direct:service-update_post").
                marshal().json().
                endRest().

            delete("/posts/{id}").
                id("delete-post").
                description("Delete post by id").
                param().name("id").type(RestParamType.path).description("Unique id of post").dataType("integer").endParam().
                responseMessage().
                    code(204).message("Operation finished successfully").
                endResponseMessage().

                route().
                to("direct:service-delete_post").
                setBody().constant(null).
                setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204)).
                endRest();
    }
}
