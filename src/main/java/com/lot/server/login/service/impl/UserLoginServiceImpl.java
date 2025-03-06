package com.lot.server.login.service.impl;

import com.lot.server.common.bean.ResultTO;
import com.lot.server.common.constant.JwtClaimsConstant;
import com.lot.server.common.properties.JwtProperties;
import com.lot.server.common.utils.JwtUtils;
import com.lot.server.employee.mapper.EmployeeMapper;
import com.lot.server.login.model.UserLoginDTO;
import com.lot.server.login.service.UserLoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserLoginServiceImpl implements UserLoginService {
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public ResultTO<String> login(UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();
        String realPassword = employeeMapper.getPasswordByUsername(username);
        System.out.println(username + realPassword);

        if(password.equals(realPassword)){
            Map<String, Object> claims = new HashMap<>();
            claims.put(JwtClaimsConstant.USER_ID, userLoginDTO.getUserId());
            String jwtToken = JwtUtils.createJWT(jwtProperties.getSecretKey(), jwtProperties.getTtl(), claims);
            return ResultTO.success(jwtToken);
        } else {
            return ResultTO.error(401,"Wrong username or password");
        }
    }
}
