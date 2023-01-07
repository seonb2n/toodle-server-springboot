package com.example.toodle_server_springboot.dto.request;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

public record UserAccountAuthenticateRequest(
        String email,
        String password
) implements Serializable {

    @Serial
    private static final long serialVersionUID = 25501815520L;

}
