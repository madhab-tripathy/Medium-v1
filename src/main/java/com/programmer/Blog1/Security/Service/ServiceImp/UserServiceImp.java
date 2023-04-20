package com.programmer.Blog1.Security.Service.ServiceImp;

import com.programmer.Blog1.Security.Exception.UserAlreadyExist;
import com.programmer.Blog1.Security.Exception.UserNotExist;
import com.programmer.Blog1.Security.Repository.UserRepository;
import com.programmer.Blog1.Security.Model.UserEntity;
import com.programmer.Blog1.Security.RequestDto.UserDto;
import com.programmer.Blog1.Security.RequestDto.UserLoginDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp {
    @Autowired
    UserRepository userRepository;
    public void createUser(UserDto userDto) throws UserAlreadyExist {
        UserEntity user = new UserEntity();

        UserDto existingUser = this.getUserByUserName(userDto.getEmail());
        if(existingUser != null){
            throw new UserAlreadyExist("User already Exist!!");
        }
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setMobile(userDto.getMobile());
        user.setPassword(userDto.getPassword());
        UserEntity createdUser = userRepository.save(user);
    }
    public String loginUser(UserLoginDto userLoginDto){
        UserEntity user;
        String email = "";
        String password = "";
        try {
            user = userRepository.findUserByEmail(userLoginDto.getEmail());
            email = user.getEmail();
            password = user.getPassword();
        }catch (NullPointerException e){
            throw new NullPointerException("error");
        }
        if(email.equals(userLoginDto.getEmail()) && password.equals(userLoginDto.getPassword())){
            return "home";
        }
        return "error";
    }
    public String forgotPassword(String email, String password){
        UserEntity updatedUser;
        try {
            updatedUser = userRepository.findUserByEmail(email);
        }catch (NullPointerException e){
            throw new NullPointerException("error");
        }
        updatedUser.setPassword(password);
        userRepository.save(updatedUser);
        return "home";
    }
    public UserDto getUserByUserName(String userName) {
        UserDto returnValue = null;
        UserEntity userEntity = userRepository.findUserByEmail(userName);
        if (userEntity != null) {
            returnValue = new UserDto();
            BeanUtils.copyProperties(userEntity, returnValue);
        }
        return returnValue;
    }
}
