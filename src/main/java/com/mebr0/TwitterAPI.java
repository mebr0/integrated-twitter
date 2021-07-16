package com.mebr0;

import lombok.NoArgsConstructor;
import org.apache.camel.builder.RouteBuilder;

@NoArgsConstructor
public class TwitterAPI extends RouteBuilder {

    @Override
    public void configure() {
        restConfiguration().
                dataFormatProperty("prettyPrint", "true").
                enableCORS(true).
                apiContextPath("/openapi").
                apiProperty("api.title", "Twitter API").
                apiProperty("api.description", "(Almost) Stateless Twitter service").
                apiProperty("api.version", "1.0.0").
                apiProperty("api.path", "");
    }
}
