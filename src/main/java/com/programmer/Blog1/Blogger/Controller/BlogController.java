package com.programmer.Blog1.Blogger.Controller;

import com.programmer.Blog1.Blogger.Exception.BlogNotFound;
import com.programmer.Blog1.Blogger.RequestDto.PostRequestDto;
import com.programmer.Blog1.Blogger.ResponseDto.BlogResponseDto;
import com.programmer.Blog1.Blogger.Service.ServiceImp.BlogServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/blogs")
public class BlogController {
    @Autowired
    private BlogServiceImp blogService;
    @GetMapping("/makePost")
    public String blogPage(){
        return "makePost";
    }
    @PostMapping("/makePost")
    public String createBlog(@ModelAttribute PostRequestDto postRequestDto){
        try {
            blogService.createBlogPost(postRequestDto);
        }catch (NullPointerException e){
            return "redirect:/blogs/makePost";
        }
        return "redirect:/blogs/makePost";
    }
    @GetMapping("/viewBlog")
    public String viewBlogById(@RequestParam int blogId, Model model){
        BlogResponseDto blogResponseDto;
        try {
            blogResponseDto = blogService.viewBlogById(blogId);
        }catch (BlogNotFound b){
            return "redirect:/error";
        }
        model.addAttribute("blogs",blogResponseDto);
        return "viewBlog";
    }

}
