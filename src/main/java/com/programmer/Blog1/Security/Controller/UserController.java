package com.programmer.Blog1.Security.Controller;

import com.programmer.Blog1.Security.ResponseDto.HomeBlogResponseDto;
import com.programmer.Blog1.Security.RequestDto.UserLoginDto;
import com.programmer.Blog1.Security.ResponseDto.UserResponseDto;
import com.programmer.Blog1.Security.Service.BlogService;
import com.programmer.Blog1.Security.Service.HomeService;
import com.programmer.Blog1.Security.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private HomeService homeService;
    @Autowired
    private BlogService blogService;
    @ModelAttribute
    private void userDetails(Model m, Principal p){
        String username = p.getName();
        UserResponseDto user = userService.getUserByUsername(username);
        m.addAttribute("user",user);
    }
    @GetMapping("/blog")
    public String home(Model model){
        List<HomeBlogResponseDto> blogResponseDtos = homeService.getAllBlogsInHomePage();
        model.addAttribute("blogs",blogResponseDtos);
        return "/user/home";
    }
    @GetMapping("/user-blogs")
    public String userBlogs(){
        return "redirect:/user/blog/my-blogs";
    }
    @GetMapping("/write-blog")
    public String writeBlogByUser(){
        return "redirect:/user/blog/make-post";
    }







    @GetMapping("/changePassword")
    public String loadChangePassword(){
        return "user/changePasscode";
    }
    @PostMapping("/updatePassword")
    public String updatePassword(Principal p,Model m,@ModelAttribute UserLoginDto loginDto){
        String result;
        String username = p.getName();
        try {
            result = userService.updateUserPasswordAfterLogin(username,loginDto);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return "redirect:/user/changePassword";
        }
        System.out.println(result);
        return "redirect:/user/changePassword";
    }



}
// NOTE: @ModelAttribute automatically load while respective loading a controller