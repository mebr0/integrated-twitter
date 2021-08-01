package com.mebr0.service;

import com.mebr0.dto.Comment;
import com.mebr0.dto.Post;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

public class CommentsService extends RouteBuilder {

    @Override
    public void configure() {
        from("direct:service-list_comments").
                to("direct:dao-list_comments");

        from("direct:service-create_comment").
                to("direct:dao-create_comment").
                enrich("direct:service-get_post", this::setPostOfComment);

        from("direct:service-get_comment").
                to("direct:dao-get_comment").
                enrich("direct:service-get_post", this::setPostOfComment);

        from("direct:service-update_comment").
                to("direct:dao-update_comment").
                enrich("direct:service-get_post", this::setPostOfComment);

        from("direct:service-delete_comment").
                to("direct:dao-delete_comment");
    }

    /**
     * Set post of comment from old {@link Exchange} by post from new {@link Exchange}
     */
    private Exchange setPostOfComment(Exchange oldExchange, Exchange newExchange) {
        var comment = oldExchange.getIn().getBody(Comment.class);
        var post = newExchange.getIn().getBody(Post.class);

        comment.setPost(post);

        oldExchange.getIn().setBody(comment);

        return oldExchange;
    }
}
