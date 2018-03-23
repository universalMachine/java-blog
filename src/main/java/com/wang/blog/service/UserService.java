package com.wang.blog.service;

import com.wang.blog.domain.LoginLog;
import com.wang.blog.DTO.UserDTO;
import com.wang.blog.domain.User;
import com.wang.blog.helper.config.security.IAuthenticationFacade;
import com.wang.blog.helper.enums.USER_LOCK;
import com.wang.blog.helper.enums.USER_TYPE;
import com.wang.blog.helper.enums.USER_CREDIT;
import com.wang.blog.repository.LoginLogRepository;
import com.wang.blog.repository.RoleRepository;
import com.wang.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    LoginLogRepository loginLogRepository;

    @Autowired
    DTOService dtoService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private  RoleRepository roleRepository;

    @Autowired
    private AuthenticationService authenticationService;



    public User getUserByUserName(String userName){
        return userRepository.getUserByUserName(userName);
    }

    public void lockUser(String userName){
        User user = userRepository.getUserByUserName(userName);
        user.setLocked(USER_LOCK.LOCK.isLocked());
        userRepository.save(user);
    }

    public void unlockUser(String userName){
        User user = userRepository.getUserByUserName(userName);
        user.setLocked(USER_LOCK.UNLOCK.isLocked());
        userRepository.save(user);
    }

    public void register(UserDTO userDTO) throws Exception{
        User user = userRepository.getUserByUserName(userDTO.getUserName());
        if(user != null){
            throw new Exception("user already exist");
        }
        user = dtoService.convertUserDtoToUser(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setCredit(USER_CREDIT.REGISTER_SUCCESS.getUpdateValue());
        user.setEnabled(true);
        user.setLocked(false);
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        userRepository.save(user);


    }

    public void loginSuccess(User user){
        updateCredit(user, USER_CREDIT.LOGIN_SUCCESS.getUpdateValue());
        LoginLog loginLog = new LoginLog();
        loginLog.setUser(user);
        loginLog.setLoginDateTime(LocalDate.now());

        userRepository.save(user);
        loginLogRepository.save(loginLog);
    }

    public void updateCredit(User user,Integer updateNum){
        user.setCredit(user.getCredit() + updateNum);
    }

    public User getDefault(){
        return userRepository.getOne(1);
    }

    public User getLoginedOrDefaultUser(){
        if(authenticationService.isLogined()){
            return userRepository.getUserByUserName(authenticationService.getUserName());
        }else {
            return getDefault();
        }
    }

}
