package com.mebr0.job;

import com.mebr0.dto.User;
import org.apache.camel.builder.RouteBuilder;

public class UsersTable extends RouteBuilder {

    @Override
    public void configure() {
        from("timer://loadUsers?repeatCount=1&delay=-1").
                routeId("pre_load_users").
                to("direct:migrations-create_users_table").
                to("direct:http-list_users").
                split(body()).
                    setBody().exchange(exchange -> {
                        var user = exchange.getIn().getBody(User.class);
                        user.setPassword(simple("{{auth.default-password}}").evaluate(exchange, String.class));
                        return user;
                    }).
                    enrich("bean:passwordEncoder?method=hash(${body.password})", (oldExchange, newExchange) -> {
                        var user = oldExchange.getIn().getBody(User.class);
                        var hashedPassword = newExchange.getIn().getBody(String.class);

                        user.setPassword(hashedPassword);

                        oldExchange.getIn().setBody(user);

                        return oldExchange;
                    }).
                    to("direct:dao-create_user").
                end().
                log("Users loaded");
    }
}
