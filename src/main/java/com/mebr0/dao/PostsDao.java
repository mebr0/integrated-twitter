package com.mebr0.dao;

import com.mebr0.dto.Comment;
import com.mebr0.dto.CommentList;
import com.mebr0.dto.Post;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.ListJacksonDataFormat;
import org.apache.camel.converter.jaxb.JaxbDataFormat;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.util.List;

public class PostsDao extends RouteBuilder {

    @Override
    public void configure() throws JAXBException {
        var jaxbCommentDataFormat = new JaxbDataFormat(JAXBContext.newInstance(Comment.class));

        from("direct:dao-list_posts").
                removeHeaders("CamelHttp*").
                removeHeaders("Accept*").
                to("{{blogs.url}}/posts?bridgeEndpoint=true").
                unmarshal(new ListJacksonDataFormat(Post.class));

        from("direct:dao-create_post").removeHeaders("CamelHttp*").
                removeHeaders("CamelHttp*").
                removeHeaders("Accept*").
                setHeader(Exchange.HTTP_METHOD, constant("POST")).
                to("{{blogs.url}}/posts?bridgeEndpoint=true").
                unmarshal().json(Post.class);

        from("direct:dao-get_post").
                removeHeaders("CamelHttp*").
                removeHeaders("Accept*").
                toD("{{blogs.url}}/posts/${headers.id}?bridgeEndpoint=true").
                unmarshal().json(Post.class);

        from("direct:dao-update_post").
                removeHeaders("CamelHttp*").
                removeHeaders("Accept*").
                setHeader(Exchange.HTTP_METHOD, constant("PUT")).
                toD("{{blogs.url}}/posts/${headers.id}?bridgeEndpoint=true").
                unmarshal().json(Post.class);

        from("direct:dao-delete_post").
                removeHeaders("CamelHttp*").
                removeHeaders("Accept*").
                setHeader(Exchange.HTTP_METHOD, constant("DELETE")).
                toD("{{blogs.url}}/posts/${headers.id}?bridgeEndpoint=true");
    }
}