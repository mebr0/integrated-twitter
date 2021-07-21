package com.mebr0.rest;

import com.mebr0.dto.User;
import org.apache.camel.builder.RouteBuilder;

public class UsersRest extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        rest().
            produces("application/json").

            get("/users").
                id("list-users").
                description("List all users").
                responseMessage().
                code(200).message("Operation finished successfully").responseModel(User[].class).
                endResponseMessage().

                route().
                to("direct:service-list_users").
                marshal().json().
                endRest();
    }
}
