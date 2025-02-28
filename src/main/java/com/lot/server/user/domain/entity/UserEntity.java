package com.lot.server.user.domain.entity;

import lombok.Data;
import java.io.Serializable;
@Data
public class UserEntity implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String role;
    private String email;
}
