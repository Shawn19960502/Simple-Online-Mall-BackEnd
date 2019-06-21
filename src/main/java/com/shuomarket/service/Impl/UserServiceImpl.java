package com.shuomarket.service.Impl;
import com.shuomarket.pojo.User;
import com.shuomarket.dao.UserMapper;
import com.shuomarket.common.ServerResponse;
import com.shuomarket.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);
        if(resultCount == 0) {
            return ServerResponse.createByErrorMessage("User name is not exist!");
        }
        //todo password MD5

        User user = userMapper.selectLogin(username, password);

        if(user == null) {
            return ServerResponse.createByErrorMessage("Invalid Password!");
        }

        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user, "login successfully");


    }
}

