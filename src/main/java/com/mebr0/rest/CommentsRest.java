package com.mebr0.rest;

import com.mebr0.dto.Comment;
import com.mebr0.dto.CommentList;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.ListJacksonDataFormat;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.model.rest.RestParamType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.util.List;

public class CommentsRest extends RouteBuilder {

    @Override
    public void configure() throws JAXBException {
        var jaxbCommentDataFormat = new JaxbDataFormat(JAXBContext.newInstance(Comment.class));

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
                removeHeaders("CamelHttp*").
                removeHeaders("Accept*").
                toD("{{blogs.url}}/posts/${headers.postId}/comments?bridgeEndpoint=true").
                unmarshal(new ListJacksonDataFormat(Comment.class)).
                setBody().exchange(exchange -> new CommentList(exchange.getIn().getBody(List.class))).
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
                removeHeaders("CamelHttp*").
                removeHeaders("Accept*").
                unmarshal(jaxbCommentDataFormat).
                marshal().json().
                setHeader(Exchange.HTTP_METHOD, constant("POST")).
                toD("{{blogs.url}}/posts/${headers.postId}/comments?bridgeEndpoint=true").
                unmarshal().json(Comment.class).
                marshal().jaxb().
                setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201)).
                endRest().

            get("/posts/{postId}/comments/{id}").
                id("retrieve-comment-of-post-by-id").
                description("Get comment of post by id").
                param().name("postId").type(RestParamType.path).description("Unique id of post").dataType("integer").endParam().
                param().name("id").type(RestParamType.path).description("Unique id of comment").dataType("integer").endParam().
                responseMessage().
                code(200).message("Operation finished successfully").responseModel(Comment.class).
                endResponseMessage().

                route().
                removeHeaders("CamelHttp*").
                removeHeaders("Accept*").
                toD("{{blogs.url}}/comments/${headers.id}?bridgeEndpoint=true").
                unmarshal().json(Comment.class).
                marshal().jaxb().
                endRest().

            put("/posts/{postId}/comments/{id}").
                id("update-comment-of-post-by-id").
                description("Update comment of post by id").
                param().name("postId").type(RestParamType.path).description("Unique id of post").dataType("integer").endParam().
                param().name("id").type(RestParamType.path).description("Unique id of comment").dataType("integer").endParam().
                param().name("body").type(RestParamType.body).description("Body of post").dataType("Post").endParam().
                responseMessage().
                code(200).message("Operation finished successfully").responseModel(Comment.class).
                endResponseMessage().
                consumes("application/xml").

                route().
                removeHeaders("CamelHttp*").
                removeHeaders("Accept*").
                unmarshal(jaxbCommentDataFormat).
                marshal().json().
                setHeader(Exchange.HTTP_METHOD, constant("PUT")).
                toD("{{blogs.url}}/comments/${headers.id}?bridgeEndpoint=true").
                unmarshal().json(Comment.class).
                marshal().jaxb().
                endRest().

            delete("/posts/{postId}/comments/{id}").
                id("delete-comment-of-post-by-id").
                description("Delete comment of post by id").
                param().name("postId").type(RestParamType.path).description("Unique id of post").dataType("integer").endParam().
                param().name("id").type(RestParamType.path).description("Unique id of comment").dataType("integer").endParam().
                responseMessage().
                code(204).message("Operation finished successfully").
                endResponseMessage().

                route().
                removeHeaders("CamelHttp*").
                removeHeaders("Accept*").
                setHeader(Exchange.HTTP_METHOD, constant("DELETE")).
                toD("{{blogs.url}}/comments/${headers.id}?bridgeEndpoint=true").
                setBody().constant(null).
                setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204)).
                endRest();
    }
}
