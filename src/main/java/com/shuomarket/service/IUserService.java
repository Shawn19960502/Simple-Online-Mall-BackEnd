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
}