package com.mebr0.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mebr0.dto.User;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.ListJacksonDataFormat;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class UsersHttp extends RouteBuilder {

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    ObjectMapper mapper;

    @Override
    public void configure() {
        from("direct:http-list_users").
                removeHeaders("CamelHttp*").
                removeHeaders("Accept*").
                to("{{blogs.url}}/users?bridgeEndpoint=true").
                unmarshal(new ListJacksonDataFormat(mapper, User.class));
    }
}
