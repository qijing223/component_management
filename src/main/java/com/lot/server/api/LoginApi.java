package com.lot.server.api;

import com.lot.server.common.bean.ResultTO;
import com.lot.server.login.model.UserLoginDTO;
import com.lot.server.login.service.UserLoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Tag(name = "User Login")
public class LoginApi {

    @Autowired
    UserLoginService userLoginService;

    @PostMapping("/login")
    @Operation(summary = "User Login")
    public ResultTO<String> login(@RequestBody UserLoginDTO userLoginDTO) {
        return userLoginService.login(userLoginDTO);
    }
}
