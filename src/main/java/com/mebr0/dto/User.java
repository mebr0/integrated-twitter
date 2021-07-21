package com.mebr0.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonPropertyOrder({ "id", "name", "username", "password", "email", "phone" })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "User", description = "User")
public class User {

    @Schema(name = "id", description = "Unique id", example = "1")
    private Long id;

    @Schema(name = "name", description = "First name", example = "Azamat")
    private String name;

    @Schema(name = "username", description = "Unique username", example = "mebr0")
    private String username;

    @Schema(name = "password", description = "Password", example = "qweqweqwe")
    private String password;

    @Schema(name = "email", description = "Unique email", example = "mebr0@gmail.com")
    private String email;

    @Schema(name = "phone", description = "Phone number", example = "7-777-777-7777")
    private String phone;
}
