package com.mebr0.cache;

import com.mebr0.dto.Comment;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.infinispan.InfinispanConstants;
import org.apache.camel.component.infinispan.InfinispanOperation;

import java.util.concurrent.TimeUnit;

public class CommentsCache extends RouteBuilder {

    @Override
    public void configure() {
        from("direct:cache-get_comment").
                setHeader(InfinispanConstants.OPERATION).constant(InfinispanOperation.GET).
                setHeader(InfinispanConstants.KEY, header("commentId")).
                to("infinispan:{{cache.comments}}").
                choice().
                    when(simple("${body} != null")).
                        unmarshal().json(Comment.class).
                    endChoice().
                end();

        from("direct:cache-put_comment").
                marshal().json().
                setHeader(InfinispanConstants.OPERATION).constant(InfinispanOperation.PUT).
                setHeader(InfinispanConstants.KEY, header("commentId")).
                setHeader(InfinispanConstants.VALUE, body()).
                setHeader(InfinispanConstants.LIFESPAN_TIME).constant(5L).
                setHeader(InfinispanConstants.LIFESPAN_TIME_UNIT).constant(TimeUnit.SECONDS.toString()).
                to("infinispan:{{cache.comments}}?resultHeader=true").
                unmarshal().json(Comment.class);

        from("direct:cache-delete_comment").
                setHeader(InfinispanConstants.OPERATION).constant(InfinispanOperation.REMOVE).
                setHeader(InfinispanConstants.KEY, header("commentId")).
                to("infinispan:{{cache.comments}}?resultHeader=true");
    }
}
