package com.programmer.Blog1.Security.Service;

import com.programmer.Blog1.Security.RequestDto.UserDto;
import com.programmer.Blog1.Security.RequestDto.UserLoginDto;
import com.programmer.Blog1.Security.ResponseDto.UserResponseDto;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


public interface UserService {
    UserResponseDto createUser(UserDto user);
    String updateUserPasswordAfterLogin(String username, UserLoginDto userLoginDto) throws Exception;
    UserResponseDto getUserByUsername(String username);
    boolean checkEmail(String email);
}
