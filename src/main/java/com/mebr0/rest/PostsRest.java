package com.mebr0.rest;

import org.apache.camel.builder.RouteBuilder;

public class PostsRest extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        rest().
            get("/posts").
                route().
                removeHeaders("CamelHttp*").
                to("https://jsonplaceholder.typicode.com/posts?bridgeEndpoint=true").
                endRest().

            get("/posts/{id}").
                route().
                removeHeaders("CamelHttp*").
                toD("https://jsonplaceholder.typicode.com/posts/${headers.id}?bridgeEndpoint=true").
                endRest();
    }
}
