package com.programmer.Blog1.Security.Controller;

import com.programmer.Blog1.Security.Model.BlogEntity;
import com.programmer.Blog1.Security.Repository.BlogRepository;
import com.programmer.Blog1.Security.RequestDto.PostRequestDto;
import com.programmer.Blog1.Security.ResponseDto.BlogResponseDto;
import com.programmer.Blog1.Security.Service.ServiceImp.BlogServiceImp;
import com.programmer.Blog1.Security.ResponseDto.UserResponseDto;
import com.programmer.Blog1.Security.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
@Controller
@RequestMapping("/user/blog")
public class BlogController {
    @Autowired
    private BlogServiceImp blogService;
    @Autowired
    private UserService userService;
    @Autowired
    private BlogRepository blogRepository;

    // here return types are html page, if the html pages are present in a directory then
    // we have to write like this folder/file.html
    @ModelAttribute
    private void userDetails(Model m, Principal p){
        if(p != null){
            String username = p.getName();
            UserResponseDto user = userService.getUserByUsername(username);
            m.addAttribute("user",user);
        }
    }
    @GetMapping("/")
    public String blogs(){
        return "/user/home";
    }
    @GetMapping("/make-post")
    public String blogPage(){
        return "blog/makePost";
    }
    @PostMapping("/make-post")
    public String createBlog(@ModelAttribute PostRequestDto postRequestDto, Principal p){
        String username = p.getName();
        try {
            BlogEntity blog = blogService.createBlogPost(username,postRequestDto);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return "redirect:/user/blog/make-post";
        }
        return "redirect:/user/blog/my-blogs";
    }
    // TODO 1: Make a getMapping with api /user/blog/{username}
    @GetMapping("/{username}")
    public String viewAllBlogsPostedByCurrentUser(Principal p,Model model){
        String username = p.getName();
        List<BlogResponseDto> blogResponseDtos = blogService.findAllBlogsPostedByCurrentUser(username);
        model.addAttribute("allBlogs",blogResponseDtos);
        return "blog/userBlogs";
    }
    @GetMapping("/my-blogs")
    public String getUserBlogUrl(Authentication authentication, Model model){
        String username = authentication.getName(); // This retrieves the current user's username
        model.addAttribute("username", username);
        return "redirect:/user/blog/"+username;
    }
    // TODO 2: update blog post
    @GetMapping("/{username}/{id}")
    public String showEditForm(@PathVariable String username,@PathVariable long id, Model model) {
        BlogEntity blog;
        try {
            blog = blogService.getBlogById(id);
        }catch (Exception e){
            return e.getMessage();
        }
        model.addAttribute("edit", blog);
        return "blog/editBlog";
    }
    @PostMapping("/{username}/{id}")
    public String updatePost(@PathVariable long id,@ModelAttribute PostRequestDto postRequestDto){
        try {
            BlogEntity blog = blogService.updateBlogPost(id,postRequestDto);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return "redirect:/user/blog/make-post";
        }
        return "redirect:/user/blog/my-blogs";
    }


}
