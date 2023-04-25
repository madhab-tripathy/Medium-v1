package com.programmer.Blog1.Security.Service.ServiceImp;

import com.programmer.Blog1.Security.Exception.UserAlreadyExist;
import com.programmer.Blog1.Security.Repository.UserRepository;
import com.programmer.Blog1.Security.Model.UserEntity;
import com.programmer.Blog1.Security.RequestDto.UserDto;
import com.programmer.Blog1.Security.RequestDto.UserLoginDto;
import com.programmer.Blog1.Security.ResponseDto.UserResponseDto;
import com.programmer.Blog1.Security.Service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.ArrayList;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncode;
    public UserResponseDto createUser(UserDto userDto) throws UserAlreadyExist {
        UserEntity user = new UserEntity();
        UserDto existingUser = this.getUserByUsername(userDto.getUsername());
        if(existingUser != null){
            throw new UserAlreadyExist("User already Exist!!");
        }
        if (checkEmail(userDto.getEmail())){
            throw new UserAlreadyExist("Email already Exist!!");
        }
        // copy userDto data to user Entity

        BeanUtils.copyProperties(userDto,user);
        // encode password
        user.setPassword(passwordEncode.encode(userDto.getPassword()));
        user.setRole("ROLE_USER");
        UserEntity createdUser = userRepository.save(user);
        UserResponseDto userResponseDto = new UserResponseDto();
        // convert to user response
        BeanUtils.copyProperties(createdUser,userResponseDto);
        return userResponseDto;
    }
    public long loginUser(UserLoginDto userLoginDto){
//        UserDto user;
//        String username = "";
//        String password = "";
//        try {
//            user = userRepository.findByUsername(userLoginDto.getUsername());
//            username = user.getUsername();
//            password = user.getPassword();
//        }catch (NullPointerException e){
//            throw new NullPointerException("error");
//        }
//        if(username.equals(userLoginDto.getUsername()) && password.equals(userLoginDto.getPassword())){
//            return user.getUserId();
//        }
        return 0;
    }
    public String forgotPassword(UserLoginDto userLoginDto){
//        UserEntity updatedUser;
//        try {
//            updatedUser = userRepository.findByUsername(userLoginDto.getUsername());
//        }catch (NullPointerException e){
//            throw new NullPointerException("error");
//        }
//        updatedUser.setPassword(userLoginDto.getPassword());
//        userRepository.save(updatedUser);
        return "home";
    }
    public UserDto getUserByUsername(String username) {
        UserDto returnValue = null;
//        UserEntity userEntity = userRepository.findByUsername(username);
//        if (userEntity != null) {
//            returnValue = new UserDto();
//            BeanUtils.copyProperties(userEntity, returnValue);
//        }
        return returnValue;
    }
    @Override
    public boolean checkEmail(String email) {
        return userRepository.existsByEmail(email);
    }

}
