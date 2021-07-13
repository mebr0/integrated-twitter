package com.mebr0.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonPropertyOrder({"id", "title", "body", "userId"})
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Post", description = "Post")
public class Post {

    @Schema(name = "id", description = "Unique id", example = "1")
    private Long id;

    @Schema(name = "title", description = "Heading of post", required = true, example = "Supernova")
    private String title;

    @Schema(name = "body", description = "Text of post", required = true, example = "Supernovas are stars that...")
    private String body;

    @Schema(name = "userId", description = "Owner id", example = "2")
    private Long userId;
}
