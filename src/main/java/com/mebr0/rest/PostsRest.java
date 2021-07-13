package com.mebr0.rest;

import com.mebr0.dto.Post;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.ListJacksonDataFormat;
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
                removeHeaders("CamelHttp*").
                removeHeaders("Accept*").
                to("{{blogs.url}}/posts?bridgeEndpoint=true").
                unmarshal(new ListJacksonDataFormat(Post.class)).
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
                removeHeaders("CamelHttp*").
                removeHeaders("Accept*").
                setHeader(Exchange.HTTP_METHOD, constant("POST")).
                to("{{blogs.url}}/posts?bridgeEndpoint=true").
                unmarshal().json(Post.class).
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
                removeHeaders("CamelHttp*").
                removeHeaders("Accept*").
                toD("{{blogs.url}}/posts/${headers.id}?bridgeEndpoint=true").
                unmarshal().json(Post.class).
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
                removeHeaders("CamelHttp*").
                removeHeaders("Accept*").
                setHeader(Exchange.HTTP_METHOD, constant("PUT")).
                toD("{{blogs.url}}/posts/${headers.id}?bridgeEndpoint=true").
                unmarshal().json(Post.class).
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
                removeHeaders("CamelHttp*").
                removeHeaders("Accept*").
                setHeader(Exchange.HTTP_METHOD, constant("DELETE")).
                toD("{{blogs.url}}/posts/${headers.id}?bridgeEndpoint=true").
                setBody().constant(null).
                setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204)).
                endRest();
    }
}
