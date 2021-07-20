package com.mebr0.service;

import org.apache.camel.builder.RouteBuilder;

public class CommentsService extends RouteBuilder {

    @Override
    public void configure() {
        from("direct:service-list_comments").
                to("direct:dao-list_comments");

        from("direct:service-create_comment").
                to("direct:dao-create_comment");

        from("direct:service-get_comment").
                to("direct:dao-get_comment");

        from("direct:service-update_comment").
                to("direct:dao-update_comment");

        from("direct:service-delete_comment").
                to("direct:dao-delete_comment");
    }
}
