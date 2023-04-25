package com.programmer.Blog1.Security.Controller;

import com.programmer.Blog1.Blogger.Controller.BlogController;
import com.programmer.Blog1.Security.Exception.UserAlreadyExist;
import com.programmer.Blog1.Security.RequestDto.UserDto;
import com.programmer.Blog1.Security.RequestDto.UserLoginDto;
import com.programmer.Blog1.Security.ResponseDto.UserResponseDto;
import com.programmer.Blog1.Security.Service.UserService;
import com.sun.xml.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    @Autowired
    UserService userService;
    @Autowired
    private BlogController blogController;
    @GetMapping("/")
    public String index(){
        return "index";
    }
    @GetMapping({"/register"})
    public String register(){
        return "register";
    }
    @PostMapping({"/createUser"})
    public String createUser(@ModelAttribute UserDto userDto, HttpSession session){
        UserResponseDto userResponseDto;
        try {
            userResponseDto = userService.createUser(userDto);
        }catch (UserAlreadyExist e){
            System.out.println(e.getMessage());
            return "redirect:/register";
        }
//        session.setAttribute(variable,message); // variable - message
        return "redirect:/register";
    }
    @GetMapping({"/signin"})
    public String login(){
        return "login";
    }
     // TODO 2: No need to redirect to blog Controller
    @RequestMapping("/allBlogs")
    public String redirectToBlogController(){
        return "redirect:/blog/";
    }



//@GetMapping("/forgot")
//public String forgotPage(){
//    return "forgot";
//}
//    @PostMapping("/forgot")
//    public String forgotPassword(@ModelAttribute UserLoginDto userLoginDto){
//        String result = "";
//        try {
//            result = userService.forgotPassword(userLoginDto);
//        }catch (NullPointerException e){
//            return "redirect:/"+e.getMessage();
//        }
//        return "redirect:/"+result;
//    }
}
