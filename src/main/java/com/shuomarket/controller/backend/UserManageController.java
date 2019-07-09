package com.shuomarket.controller.backend;

import com.shuomarket.common.Const;
import com.shuomarket.common.ServerResponse;
import com.shuomarket.pojo.User;
import com.shuomarket.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/user")
public class UserManageController {

    @Autowired
    IUserService iUserService;
    @RequestMapping(value="login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String userName, String passWord, HttpSession session) {
        ServerResponse<User> response = iUserService.login(userName, passWord);
        if(response.isSuccess()) {
            User user = response.getData();
            if(user.getRole() == Const.Role.ROLE_ADMIN) {
                session.setAttribute(Const.CURRENT_USER, user);
            } else {
                return ServerResponse.createByErrorMessage("Not administrator, can't login");
            }
        }
        return response;
    }
}
