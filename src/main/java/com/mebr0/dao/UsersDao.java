package com.mebr0.dao;

import com.mebr0.dto.User;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.GroupedBodyAggregationStrategy;
import org.postgresql.util.PSQLException;

public class UsersDao extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:dao-list_users").
                to("sql:classpath:sql/list_users.sql").
                split(body(), new GroupedBodyAggregationStrategy()).
                    setBody().exchange(User::from).
                end();

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
