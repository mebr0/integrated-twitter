package com.mebr0.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.camel.Exchange;

import java.util.Map;

@JsonPropertyOrder({ "id", "name", "username", "email", "phone" })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "User", description = "User")
public class User {

    @Schema(name = "id", description = "Unique id", required = true, example = "1")
    private Long id;

    @Schema(name = "name", description = "First name", required = true, example = "Azamat")
    private String name;

    @Schema(name = "username", description = "Unique username", required = true, example = "mebr0")
    private String username;

    @JsonIgnore
    @Schema(name = "password", description = "Password", hidden = true, example = "qweqweqwe")
    private String password;

    @Schema(name = "email", description = "Unique email", required = true, example = "mebr0@gmail.com")
    private String email;

    @Schema(name = "phone", description = "Phone number", required = true,   example = "7-777-777-7777")
    private String phone;

    /**
     * Create {@link User} from {@link Map} in body of {@link Exchange}
     */
    public static User from(Exchange exchange) {
        var row = exchange.getIn().getBody(Map.class);

        return new User(((Integer) row.get("id")).longValue(), (String) row.get("name"), (String) row.get("username"),
                null, (String) row.get("email"), (String) row.get("phone"));
    }
}
