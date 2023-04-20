package com.programmer.Blog1.Security.Controller;

import com.programmer.Blog1.Security.Exception.UserAlreadyExist;
import com.programmer.Blog1.Security.Exception.UserNotExist;
import com.programmer.Blog1.Security.RequestDto.UserDto;
import com.programmer.Blog1.Security.RequestDto.UserLoginDto;
import com.programmer.Blog1.Security.Service.ServiceImp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class UserController {
    @Autowired
    private UserServiceImp userService;
    @GetMapping("/home")
    public String homePage(){
        return "index";
    }
    @GetMapping("/error")
    public String errorPage(){
        return "error";
    }
    @GetMapping("/register")
    public String registerPage(){
        return "register";
    }
    @PostMapping("/register")
    public String createUser(@ModelAttribute UserDto userDto){
        try {
            userService.createUser(userDto);
        }catch (UserAlreadyExist e){
            return "redirect:/register";
        }
        return "redirect:/home";
    }
    @GetMapping("login")
    public String loginPage(){
        return "login";
    }
    @PostMapping("/login")
    public String loginUser(@ModelAttribute UserLoginDto userLoginDto){
        String result = "";
        try {
            result = userService.loginUser(userLoginDto);;
        }catch (NullPointerException e){
            return "redirect:/"+e.getMessage();
        }
        return "redirect:/"+result;
    }
    @GetMapping("/forgot")
    public String forgotPage(){
        return "forgot";
    }
    @PostMapping("/forgot")
    public String forgotPassword(@RequestParam String email, @RequestParam String password){
        String result = "";
        try {
            result = userService.forgotPassword(email,password);
        }catch (NullPointerException e){
            return "redirect:/"+e.getMessage();
        }
        return "redirect:/"+result;
    }
}
