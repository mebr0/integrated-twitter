package com.mebr0.cache;

import com.mebr0.dto.Post;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.infinispan.InfinispanConstants;
import org.apache.camel.component.infinispan.InfinispanOperation;

import java.util.concurrent.TimeUnit;

public class PostsCache extends RouteBuilder {

    @Override
    public void configure() {
        from("direct:cache-get_post").
                setHeader(InfinispanConstants.OPERATION).constant(InfinispanOperation.GET).
                setHeader(InfinispanConstants.KEY, simple("${headers.postId}")).
                to("infinispan:{{cache.posts}}").
                choice().
                    when(simple("${body} != null")).
                        unmarshal().json(Post.class).
                    endChoice().
                end();

        from("direct:cache-put_post").
                marshal().json().
                setHeader(InfinispanConstants.OPERATION).constant(InfinispanOperation.PUT).
                setHeader(InfinispanConstants.KEY, simple("${headers.postId}")).
                setHeader(InfinispanConstants.VALUE, body()).
                setHeader(InfinispanConstants.LIFESPAN_TIME).constant(5L).
                setHeader(InfinispanConstants.LIFESPAN_TIME_UNIT).constant(TimeUnit.SECONDS.toString()).
                to("infinispan:{{cache.posts}}?resultHeader=true").
                unmarshal().json(Post.class);

        from("direct:cache-delete_post").
                setHeader(InfinispanConstants.OPERATION).constant(InfinispanOperation.REMOVE).
                setHeader(InfinispanConstants.KEY, header("postId")).
                to("infinispan:{{cache.posts}}?resultHeader=true");
    }
}
