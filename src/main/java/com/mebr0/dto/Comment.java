package com.mebr0.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "comment")
@XmlType(propOrder = { "id", "name", "body", "email", "postId" })
@XmlAccessorType(XmlAccessType.FIELD)
@JsonPropertyOrder({ "id", "name", "body", "email", "postId" })
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Comment", description = "Comment")
public class Comment {

    @Schema(name = "id", description = "Unique id", example = "1")
    @XmlElement(name = "id")
    private Long id;

    @Schema(name = "name", description = "Heading of post", required = true, example = "I don't agree")
    @XmlElement(name = "name")
    private String name;

    @Schema(name = "body", description = "Owner id", required = true, example = "You're wrong...")
    @XmlElement(name = "body")
    private String body;

    @Schema(name = "email", description = "Text of post", example = "bob@gmail.com")
    @XmlElement(name = "email")
    private String email;

    @Schema(name = "postId", description = "Owner id", example = "2")
    @XmlElement(name = "postId")
    private Long postId;
}
