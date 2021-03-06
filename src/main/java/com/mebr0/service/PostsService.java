package com.mebr0.service;

import org.apache.camel.builder.RouteBuilder;

public class PostsService extends RouteBuilder {

    @Override
    public void configure() {
        from("direct:service-list_posts").
                to("direct:dao-list_posts");

        from("direct:service-create_post").
                to("direct:dao-create_post");

        from("direct:service-get_post").
                to("direct:cache-get_post").
                choice().
                    when(simple("${body} == null")).
                        to("direct:dao-get_post").
                        to("direct:cache-put_post").
                    endChoice().
                end();

        from("direct:service-update_post").
                to("direct:dao-update_post").
                to("direct:cache-delete_post");

        from("direct:service-delete_post").
                to("direct:dao-delete_post").
                to("direct:cache-delete_post");
    }
}
