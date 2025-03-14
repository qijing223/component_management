package com.lot.server.login.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "User Login DTO")
public class UserLoginDTO {
//    @Schema(description = "User Id")
//    private Integer userId;
    @Schema(description = "User Name(Employee ID?)")
    private String username;
    @Schema(description = "Password")
    private String password;
}