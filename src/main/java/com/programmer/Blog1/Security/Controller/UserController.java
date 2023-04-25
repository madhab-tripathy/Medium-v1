package com.programmer.Blog1.Security.Controller;

import com.programmer.Blog1.Blogger.Controller.BlogController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/")
    public String home(){
        return "user/home";
    }
    @GetMapping("/userBlogs")
    public String userBlogs(){
        return "redirect:/user/blog/";
    }
}
