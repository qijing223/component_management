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
        String employeeName = userLoginDTO.getEmployeeName();
        String password = userLoginDTO.getPassword();
        String realPassword = employeeMapper.getPasswordByEmployeeName(employeeName);
        Integer employeeId = employeeMapper.getEmployeeIdByUsername(employeeName);
        System.out.println("Login User: " + employeeName + " " + employeeId);

        if(password.equals(realPassword)){
            Map<String, Object> claims = new HashMap<>();
            claims.put(JwtClaimsConstant.USER_ID, employeeId);
            String jwtToken = JwtUtils.createJWT(jwtProperties.getSecretKey(), jwtProperties.getTtl(), claims);
            return ResultTO.success(jwtToken);
        } else {
            return ResultTO.error(401,"Wrong employee name or password");
        }
    }
}
