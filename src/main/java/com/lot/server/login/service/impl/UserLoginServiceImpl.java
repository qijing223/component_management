package com.lot.server.login.service.impl;

import com.lot.server.common.bean.ResultTO;
import com.lot.server.login.model.UserLoginDTO;
import com.lot.server.login.service.UserLoginService;
import org.springframework.stereotype.Service;

@Service
public class UserLoginServiceImpl implements UserLoginService {
    @Override
    public ResultTO<Void> login(UserLoginDTO userLoginDTO) {
        return new ResultTO<Void>(200, "", null);
    }
}
