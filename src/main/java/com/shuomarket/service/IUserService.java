package com.shuomarket.service;
import com.shuomarket.pojo.User;
import com.shuomarket.common.ServerResponse;

/**
 * Created by Shawn Yang
 */
public interface IUserService {
    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(User user);

    ServerResponse<String> checkValid(String str, String type);

    ServerResponse<String> selectQuestion(String username);

    ServerResponse<String> checkAnswer(String username, String question, String answer);

    ServerResponse<String> forgetRessetPassword(String username, String passwordNew, String forgetToken);

    ServerResponse<String> resetPassword(String passWordOld, String passWordNew, User user);

    ServerResponse<User> updateInformation(User user);

    ServerResponse<User> getInformation(Integer userId);

    ServerResponse checkAdminRole(User user);
}