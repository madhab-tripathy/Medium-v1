package com.programmer.Blog1.Security.Controller;

import com.programmer.Blog1.Security.ResponseDto.HomeBlogResponseDto;
import com.programmer.Blog1.Security.Exception.UserAlreadyExist;
import com.programmer.Blog1.Security.RequestDto.UserDto;
import com.programmer.Blog1.Security.RequestDto.UserLoginDto;
import com.programmer.Blog1.Security.ResponseDto.UserResponseDto;
import com.programmer.Blog1.Security.Service.BlogService;
import com.programmer.Blog1.Security.Service.HomeService;
import com.programmer.Blog1.Security.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    UserService userService;
    @Autowired
    private HomeService homeService;
    @Autowired
    private BlogService blogService;
    @ModelAttribute
    private void userDetails(Model m, Principal p){
        if(p != null){
            String username = p.getName();
            UserResponseDto user = userService.getUserByUsername(username);
            m.addAttribute("user",user);
        }
    }
    @GetMapping({"/","/blog/"})
    public String index(Model model){
        List<HomeBlogResponseDto> blogResponseDtos = homeService.getAllBlogsInHomePage();
        model.addAttribute("blogs",blogResponseDtos);
        List<HomeBlogResponseDto> trendingBlogs = homeService.getTrendingBlogs();
        model.addAttribute("trending",trendingBlogs);
        return "index";
    }
    @GetMapping("/register")
    public String register(){
        return "register";
    }
    @PostMapping("/createUser")
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
    @GetMapping("/view-blog")
    public String viewBlog(@RequestParam long id,Model model){
        HomeBlogResponseDto homeBlogResponseDto = null;
        try {
            homeBlogResponseDto = blogService.viewBlogs(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        model.addAttribute("readBlog",homeBlogResponseDto);
        return "/blog/readBlog";
    }
    @GetMapping("/signin")
    public String login(Authentication authentication){
        if (authentication != null && authentication.isAuthenticated()) {
            // User is already authenticated, redirect to home page
            return "redirect:/user/";
        }
        // User is not authenticated, show login page
        return "login";
    }
    @GetMapping("/homeChangePassword")
    public String loadChangePassword(){
        return "user/changePasscode";
    }
    @PostMapping("/homeUpdatePassword")
    public String updatePassword(@ModelAttribute UserLoginDto loginDto,Model model){
        String result;
        try {
            result = homeService.updateUserPasswordBeforeLogin(loginDto);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return "redirect:/homeChangePassword";
        }
        System.out.println(result);
        return "redirect:/homeChangePassword";
    }
}
