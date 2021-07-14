package com.mebr0.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "comments")
@XmlType(propOrder = { "comments" })
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "CommentList", description = "List of comments")
public class CommentList {

    @Schema(name = "comment", description = "Comments", required = true)
    @XmlElement(name = "comment")
    private List<Comment> comments;
}
