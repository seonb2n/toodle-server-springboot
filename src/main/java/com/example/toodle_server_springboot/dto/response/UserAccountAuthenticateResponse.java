package com.example.toodle_server_springboot.dto.response;

import java.io.Serial;
import java.io.Serializable;

public record UserAccountAuthenticateResponse(
        String jwtToken
) implements Serializable {

    @Serial
    private static final long serialVersionUID = 25501815520L;

}
