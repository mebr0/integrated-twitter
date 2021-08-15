package com.mebr0.dao;

import com.mebr0.dto.Comment;
import com.mebr0.dto.CommentList;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.ListJacksonDataFormat;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.spi.DataFormat;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.util.List;

public class CommentsDao extends RouteBuilder {

    private final DataFormat jaxbCommentDataFormat;

    public CommentsDao() throws JAXBException {
        jaxbCommentDataFormat = new JaxbDataFormat(JAXBContext.newInstance(Comment.class));
    }

    @Override
    public void configure() {
        from("direct:dao-list_comments").
                removeHeaders("CamelHttp*").
                removeHeaders("Accept*").
                toD("{{blogs.url}}/posts/${headers.postId}/comments?bridgeEndpoint=true").
                unmarshal(new ListJacksonDataFormat(Comment.class)).
                setBody().exchange(exchange -> new CommentList(exchange.getIn().getBody(List.class)));

        from("direct:dao-create_comment").
                removeHeaders("CamelHttp*").
                removeHeaders("Accept*").
                unmarshal(jaxbCommentDataFormat).
                marshal().json().
                setHeader(Exchange.HTTP_METHOD, constant("POST")).
                toD("{{blogs.url}}/posts/${headers.postId}/comments?bridgeEndpoint=true").
                unmarshal().json(Comment.class);

        from("direct:dao-get_comment").
                removeHeaders("CamelHttp*").
                removeHeaders("Accept*").
                toD("{{blogs.url}}/comments/${headers.commentId}?bridgeEndpoint=true").
                unmarshal().json(Comment.class);

        from("direct:dao-update_comment").
                removeHeaders("CamelHttp*").
                removeHeaders("Accept*").
                unmarshal(jaxbCommentDataFormat).
                marshal().json().
                setHeader(Exchange.HTTP_METHOD, constant("PUT")).
                toD("{{blogs.url}}/comments/${headers.commentId}?bridgeEndpoint=true").
                unmarshal().json(Comment.class);

        from("direct:dao-delete_comment").
                removeHeaders("CamelHttp*").
                removeHeaders("Accept*").
                setHeader(Exchange.HTTP_METHOD, constant("DELETE")).
                toD("{{blogs.url}}/comments/${headers.commentId}?bridgeEndpoint=true");
    }
}
