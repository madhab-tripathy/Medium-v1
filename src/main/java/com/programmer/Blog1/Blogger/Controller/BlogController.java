package com.programmer.Blog1.Blogger.Controller;

import com.programmer.Blog1.Blogger.Model.BlogEntity;
import com.programmer.Blog1.Blogger.Repository.BlogRepository;
import com.programmer.Blog1.Blogger.RequestDto.PostRequestDto;
import com.programmer.Blog1.Blogger.ResponseDto.BlogResponseDto;
import com.programmer.Blog1.Blogger.Service.ServiceImp.BlogServiceImp;
import com.programmer.Blog1.Security.Model.UserEntity;
import com.programmer.Blog1.Security.Repository.UserRepository;
import com.programmer.Blog1.Security.ResponseDto.UserResponseDto;
import com.programmer.Blog1.Security.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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
            blogService.makeUrlForBlog(username,blog);
        }catch (NullPointerException e){
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
    @GetMapping("/{username}/{title}")
    public String showEditForm(@PathVariable String title, Model model) {
        BlogEntity blog = blogRepository.findByTitle(title);
        model.addAttribute("edit", blog);
        return "blog/editBlog";
    }
    @PostMapping("{username}/edit-post")
    public String updatePost(Principal p,@ModelAttribute PostRequestDto postRequestDto){
        String username = p.getName();
        try {
            blogService.updateBlogPost(username,postRequestDto);
        }catch (NullPointerException e){
            return "redirect:/user/blog/make-post";
        }
        return "redirect:/user/blog/my-blogs";
    }
    // TODO 3: Delete blog post

}
