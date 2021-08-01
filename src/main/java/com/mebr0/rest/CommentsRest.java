package com.mebr0.rest;

import com.mebr0.dto.Comment;
import com.mebr0.dto.CommentList;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestParamType;

public class CommentsRest extends RouteBuilder {

    @Override
    public void configure() {
        rest().
            produces("application/xml").

            get("/posts/{postId}/comments").
                id("list-comments-of-post").
                description("List all comments of post").
                param().name("postId").type(RestParamType.path).description("Unique id of post").dataType("integer").endParam().
                responseMessage().
                code(200).message("Operation finished successfully").responseModel(CommentList.class).
                endResponseMessage().

                route().
                to("direct:service-list_comments").
                marshal().jaxb().
                endRest().

            post("/posts/{postId}/comments").
                id("create-comment-of-post").
                description("Create new comment of post").
                param().name("postId").type(RestParamType.path).description("Unique id of post").dataType("integer").endParam().
                param().name("body").type(RestParamType.body).description("Body of comment").dataType("Comment").endParam().
                responseMessage().
                code(201).message("Operation finished successfully").responseModel(Comment.class).
                endResponseMessage().
                consumes("application/xml").

                route().
                to("direct:service-create_comment").
                marshal().jaxb().
                setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201)).
                endRest().

            get("/posts/{postId}/comments/{commentId}").
                id("retrieve-comment-of-post-by-id").
                description("Get comment of post by id").
                param().name("postId").type(RestParamType.path).description("Unique id of post").dataType("integer").endParam().
                param().name("commentId").type(RestParamType.path).description("Unique id of comment").dataType("integer").endParam().
                responseMessage().
                code(200).message("Operation finished successfully").responseModel(Comment.class).
                endResponseMessage().

                route().
                to("direct:service-get_comment").
                marshal().jaxb().
                endRest().

            put("/posts/{postId}/comments/{commentId}").
                id("update-comment-of-post-by-id").
                description("Update comment of post by id").
                param().name("postId").type(RestParamType.path).description("Unique id of post").dataType("integer").endParam().
                param().name("commentId").type(RestParamType.path).description("Unique id of comment").dataType("integer").endParam().
                param().name("body").type(RestParamType.body).description("Body of post").dataType("Post").endParam().
                responseMessage().
                code(200).message("Operation finished successfully").responseModel(Comment.class).
                endResponseMessage().
                consumes("application/xml").

                route().
                to("direct:service-update_comment").
                marshal().jaxb().
                endRest().

            delete("/posts/{postId}/comments/{commentId}").
                id("delete-comment-of-post-by-id").
                description("Delete comment of post by id").
                param().name("postId").type(RestParamType.path).description("Unique id of post").dataType("integer").endParam().
                param().name("commentId").type(RestParamType.path).description("Unique id of comment").dataType("integer").endParam().
                responseMessage().
                code(204).message("Operation finished successfully").
                endResponseMessage().

                route().
                to("direct:service-delete_comment").
                setBody().constant(null).
                setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204)).
                endRest();
    }
}
