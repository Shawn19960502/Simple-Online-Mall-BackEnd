package com.shuomarket.service.Impl;
import com.shuomarket.common.Const;
import com.shuomarket.common.TokenCache;
import com.shuomarket.pojo.User;
import com.shuomarket.dao.UserMapper;
import com.shuomarket.common.ServerResponse;
import com.shuomarket.service.IUserService;
import com.shuomarket.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);
        if(resultCount == 0) {
            return ServerResponse.createByErrorMessage("User name does not exist!");
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


    @Override
    public ServerResponse<String> selectQuestion(String username) {

        ServerResponse validResponse = this.checkValid(username, Const.USERNAME);
        if(validResponse.isSuccess()) {
            return ServerResponse.createByErrorMessage("user not exists.");
        }
        String question = userMapper.selectQuestionByUsername(username);
        if(StringUtils.isNotBlank(question)) {
            return ServerResponse.createBySuccess(question);
        }
        return ServerResponse.createByErrorMessage("Question is Empty!");
    }

    @Override
    public ServerResponse<String> checkAnswer(String username, String question, String answer) {
        int resultCount = userMapper.checkAnswer(username, question, answer);
        if(resultCount > 0) {
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.TOKEN_PREFIX + username, forgetToken);
            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByErrorMessage("Answer to Question is not correct");

    }

    @Override
    public ServerResponse<String> forgetRessetPassword(String username, String passwordNew, String forgetToken) {
        if(StringUtils.isBlank(forgetToken)) {
            return ServerResponse.createByErrorMessage("Token is not passed.");
        }
        ServerResponse validResponse = this.checkValid(username, Const.USERNAME);
        if(validResponse.isSuccess()) {
            return ServerResponse.createByErrorMessage("User does not exist!");
        }

        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX+ username);
        if(StringUtils.isBlank(token)) {
            return ServerResponse.createByErrorMessage("Token is invalid");
        }
        if(StringUtils.equals(forgetToken, token)) {
            String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
            int rowCount = userMapper.updatePasswordByUsername(username, md5Password);
            if(rowCount > 0) {
                return ServerResponse.createBySuccessMessage("Update password successfully");
            }
        } else {
            return ServerResponse.createByErrorMessage("Token is invalid");
        }
        return ServerResponse.createByErrorMessage("Update password failed");
    }

    @Override
    public ServerResponse<String> resetPassword(String passWordOld, String passWordNew, User user) {
        int resultCount = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passWordOld), user.getId());
        if(resultCount == 0) {
            return ServerResponse.createByErrorMessage("Old password is not correct");
        } else {
            user.setPassword(MD5Util.MD5EncodeUtf8(passWordNew));
            int updateCount = userMapper.updateByPrimaryKeySelective(user);
            if(updateCount > 0) {
                return ServerResponse.createBySuccessMessage("update successfully");
            } else {
                return ServerResponse.createByErrorMessage("update failed");
            }
        }
    }

    @Override
    public ServerResponse<User> updateInformation(User user) {
        int resultCount = userMapper.checkEmailByUserId(user.getEmail(), user.getId());
        if(resultCount > 0) {
            return ServerResponse.createByErrorMessage("Email already exists!");
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());
        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);

        if(updateCount > 0) {
            return ServerResponse.createBySuccess( updateUser,"update successfully!");
        } else {
            return ServerResponse.createByErrorMessage("update failed");
        }
    }

    @Override
    public ServerResponse<User> getInformation(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if(user == null) {
            return ServerResponse.createByErrorMessage("Can't find user");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);
    }

    //backend

    /**
     * Check if it's administrator.
     * @param user user
     * @return
     */
    @Override
    public ServerResponse checkAdminRole(User user){
        if(user != null && user.getRole().intValue() == Const.Role.ROLE_ADMIN){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }
}

