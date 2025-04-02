package com.lot.server.login.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "User Login DTO")
public class UserLoginDTO {
//    @Schema(description = "Employee Id")
//    private Integer employeeId;
    @Schema(description = "Employee Name")
    @JsonProperty("employee_name")
    private String employeeName;
    @Schema(description = "Password")
    @JsonProperty("password")
    private String password;
}