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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncode;
    public UserResponseDto createUser(UserDto userDto) throws UserAlreadyExist {
        UserEntity user = new UserEntity();
        UserResponseDto existingUser = this.getUserByUsername(userDto.getUsername());
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
    public String updateUserPasswordAfterLogin(String username,UserLoginDto userLoginDto) throws Exception {
        String oldPass = userLoginDto.getOldPassword();
        String newPass = userLoginDto.getNewPassword();
        if(!oldPass.isBlank() && !newPass.isBlank()){
            UserEntity user = userRepository.findByUsername(username);
            boolean isValidOldPass = passwordEncode.matches(oldPass,user.getPassword());
            boolean isOldAndNewPassSame = passwordEncode.matches(newPass,user.getPassword());
            if(isOldAndNewPassSame){
                throw new Exception("Password must be different from previous password");
            }
            if(isValidOldPass){
                user.setPassword(passwordEncode.encode(newPass));
                userRepository.save(user);
                return "Password update successfully...";
            }else {
                throw new Exception("Password Mismatch");
            }
        }
        throw new Exception("Invalid inputs!!");
    }
    public UserResponseDto getUserByUsername(String username) {
        UserResponseDto returnValue = null;
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity != null) {
            returnValue = new UserResponseDto();
            BeanUtils.copyProperties(userEntity, returnValue);
        }
        return returnValue;
    }
    @Override
    public boolean checkEmail(String email) {
        return userRepository.existsByEmail(email);
    }

}
