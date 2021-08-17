package com.mebr0.service;

import org.apache.camel.builder.RouteBuilder;

public class UsersService extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:service-list_users").
                to("direct:dao-list_users");
    }
}
