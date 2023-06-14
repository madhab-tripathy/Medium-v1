package com.programmer.Blog1.Security.Controller;

import com.programmer.Blog1.Security.Model.BlogEntity;
import com.programmer.Blog1.Security.Model.UserEntity;
import com.programmer.Blog1.Security.RequestDto.PostRequestDto;
import com.programmer.Blog1.Security.ResponseDto.BlogResponseDto;
import com.programmer.Blog1.Security.ResponseDto.HomeBlogResponseDto;
import com.programmer.Blog1.Security.Service.BlogService;
import com.programmer.Blog1.Security.ResponseDto.UserResponseDto;
import com.programmer.Blog1.Security.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
@Controller
@RequestMapping("/user/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private UserService userService;

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
//    @GetMapping("/")
//    public String blogs(){
//        return "/user/home";
//    }
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

    @GetMapping("/view-blog")
    public String viewBlog(@RequestParam long id,Model model){
        HomeBlogResponseDto homeBlogResponseDto = null;
        try {
            homeBlogResponseDto = blogService.viewBlogs(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        model.addAttribute("readBlog",homeBlogResponseDto);
        return "blog/readBlog";
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam long id, Model model,Authentication authentication) {
        BlogEntity blog;
        try {
            blog = blogService.getBlogById(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return "redirect:userBlogs";
        }
        String username = blog.getUserEntity().getUsername();
        if (!authentication.getName().equals(username)) {
            return "redirect:userBlogs";
        }
        model.addAttribute("edit", blog);
        return "blog/editBlog";
    }
    // TODO 2: update blog post
    @PostMapping("/update")
    public String updatePost(@RequestParam long id,@ModelAttribute PostRequestDto postRequestDto, Authentication authentication){
        try {
            BlogEntity blog = blogService.updateBlogPost(id,postRequestDto);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return "redirect:/user/blog/make-post";
        }
        return "redirect:/user/blog/my-blogs";
    }
//    TODO 3: Delete blogs
    @GetMapping("/delete")
    public String deletePost(@RequestParam long id){
        blogService.deletePostById(id);
        return "redirect:/user/user-blogs";
    }
}
