package com.programmer.Blog1.Security.Service;

import com.programmer.Blog1.Security.RequestDto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto user);
    UserDto getUserByUserName(String userName);
}
