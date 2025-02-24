package com.lot.server.login.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "User Login DTO")
public class UserLoginDTO {
    @Schema(description = "User Name")
    private String username;
    @Schema(description = "Password")
    private String password;
}
