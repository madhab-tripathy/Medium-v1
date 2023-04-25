package com.programmer.Blog1.Security.Config;

import com.programmer.Blog1.Security.Model.UserEntity;
import com.programmer.Blog1.Security.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
    @Autowired
    UserRepository userRepository1;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository1.findByUsername(username);
        if (user != null){
            return new CustomUserDetails(user);
        }
        throw new UsernameNotFoundException("username not found!!");
    }
}
