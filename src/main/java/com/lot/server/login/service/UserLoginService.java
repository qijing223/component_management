package com.lot.server.login.service;


import com.lot.server.common.bean.ResultTO;
import com.lot.server.login.model.UserLoginDTO;

public interface UserLoginService {
    public ResultTO<String> login(UserLoginDTO userLoginDTO);
}
