package com.mebr0.dao;

import org.apache.camel.builder.RouteBuilder;
import org.postgresql.util.PSQLException;

public class UsersDao extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:dao-create_user").
                // Handle SQL exceptions for duplications
                onException(PSQLException.class).
                    handled(true).
                end().
                to("sql:classpath:sql/create_user.sql");

        from("direct:migrations-create_users_table").
                to("sql:classpath:sql/create_users_table.sql");
    }
}
