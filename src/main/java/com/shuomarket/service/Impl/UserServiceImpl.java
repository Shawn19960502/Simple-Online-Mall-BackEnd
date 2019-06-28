package com.shuomarket.service.Impl;
import com.shuomarket.common.Const;
import com.shuomarket.pojo.User;
import com.shuomarket.dao.UserMapper;
import com.shuomarket.common.ServerResponse;
import com.shuomarket.service.IUserService;
import com.shuomarket.util.MD5Util;
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
        String md5Password = MD5Util.MD5EncodeUtf8(password);

        User user = userMapper.selectLogin(username, md5Password);

        if(user == null) {
            return ServerResponse.createByErrorMessage("Invalid Password!");
        }

        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user, "login successfully");

    }

    @Override
    public ServerResponse<String> register(User user) {
        ServerResponse<String> validResponse = this.checkValid(user.getUsername(), Const.USERNAME);
        if(!validResponse.isSuccess()) {
            return validResponse;
        }
        validResponse = this.checkValid(user.getEmail(), Const.EMAIL);
        if(!validResponse.isSuccess()) {
            return validResponse;
        }
        user.setRole(Const.Role.ROLE_CUSMTER);

        // MD5 Encryption.
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));

        int resultCount = userMapper.insert(user);

        if(resultCount == 0) {
            return ServerResponse.createByErrorMessage("Register Failed.");
        }
        return ServerResponse.createBySuccessMessage("Register Succeed.");
    }

    @Override
    public ServerResponse<String> checkValid(String str, String type) {
        if (StringUtils.isNotBlank(type)) {
            if(Const.USERNAME.equals(type)) {
                int resultCount = userMapper.checkUsername(str);
                if(resultCount > 0) {
                    return ServerResponse.createByErrorMessage("User already exists.");
                }
            }
            else if(Const.EMAIL.equals(type)) {
                int resultCount = userMapper.checkEmail(str);
                if(resultCount > 0) {
                    return ServerResponse.createByErrorMessage("Email already exists.");
                }
            }
        } else {
            return ServerResponse.createByErrorMessage("Argument Error.");
        }
        return ServerResponse.createBySuccessMessage("Argument Check Successful");
    }
}

