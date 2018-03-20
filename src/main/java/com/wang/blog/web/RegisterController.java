package com.wang.blog.web;

import com.wang.blog.DTO.UserDTO;
import com.wang.blog.domain.User;
import com.wang.blog.result.Data;
import com.wang.blog.result.Result;
import com.wang.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin
@RestController
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO) {
        Result result = new Result();
        try {
            userService.register(userDTO);
            User user = userService.getUserByUserName(userDTO.getUserName());

            userDTO.setCredit(user.getCredit());
            userDTO.setLocked(user.isLocked());
            result.setData(new Data<UserDTO>(userDTO));
            result.setReturnCode(200);
        } catch (Exception e) {
            result.setReturnCode(7000);
            result.setReturnMessage(e.getMessage());
        }

        return result;

    }
}
