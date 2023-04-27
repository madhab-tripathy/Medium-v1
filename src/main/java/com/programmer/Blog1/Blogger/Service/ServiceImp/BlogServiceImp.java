package com.programmer.Blog1.Blogger.Service.ServiceImp;

import com.programmer.Blog1.Blogger.Exception.BlogNotFound;
import com.programmer.Blog1.Blogger.Model.BlogEntity;
import com.programmer.Blog1.Blogger.Repository.BlogRepository;
import com.programmer.Blog1.Blogger.RequestDto.PostRequestDto;
import com.programmer.Blog1.Blogger.ResponseDto.BlogResponseDto;
import com.programmer.Blog1.Security.Model.UserEntity;
import com.programmer.Blog1.Security.Repository.UserRepository;
import org.jsoup.safety.Safelist;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
import java.util.ArrayList;

import org.jsoup.Jsoup;

@Service
public class BlogServiceImp {
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private UserRepository userRepository;
    private final Pattern IMAGE_URL_PATTERN = Pattern.compile("(?i)\\b((?:https?://|www\\d*\\.|m\\.)\\S+\\.(?:jpg|jpeg|png|gif|bmp|svg|webp|jfif))\\b");
    public BlogEntity createBlogPost(String username,PostRequestDto postRequestDto){
        BlogEntity blog = new BlogEntity();
        String title = postRequestDto.getTitle();
        String content = postRequestDto.getContents();
        String description = postRequestDto.getDescription();
        if(title.isBlank()){
            throw new NullPointerException("Must Contain a title");
        }
        if (content.isBlank()){
            throw new NullPointerException("Must Contain a Content");
        }
        if (description.isBlank()){
            throw new NullPointerException("Must Contain a description");
        }
        UserEntity user = userRepository.findByUsername(username);
        saveBlog(blog,user,content,title,description);
        return blog;
    }
    private void saveBlog(BlogEntity blog, UserEntity user,String content,String title, String description){
        blog.setContents(content);
        blog.setTitle(title);
        blog.setDescription(description);
        blog.setPubDate(new Date());
        blog.setUserEntity(user);
        List<BlogEntity> blogList = user.getBlogList();
        blogList.add(blog);
        user.setBlogList(blogList);
        userRepository.save(user);
    }
    public void updateBlogPost(String username,PostRequestDto postRequestDto){
        String title = postRequestDto.getTitle();
        String content = postRequestDto.getContents();
        String description = postRequestDto.getDescription();
        if(title.isBlank()){
            throw new NullPointerException("Must Contain a title");
        }
        if (content.isBlank()){
            throw new NullPointerException("Must Contain a Content");
        }
        if (description.isBlank()){
            throw new NullPointerException("Must Contain a description");
        }
        UserEntity user = userRepository.findByUsername(username);
        String uri = user.getUrl();
        List<BlogEntity> blogs = user.getBlogList();
        for(BlogEntity blog : blogs){
            String blogPattern = "/"+username+"/"+blog.getTitle();
            if(uri.equals(blogPattern)){
                blog.setContents(content);
                blog.setTitle(title);
                blog.setDescription(description);
                blog.setPubDate(new Date());
                blogRepository.save(blog);
                return;
            }
        }
    }
    public void makeUrlForBlog(String username,BlogEntity blog){
        String blogTitle = blog.getTitle();
        String blogUrl = "/"+username+"/"+blogTitle;
        UserEntity user = userRepository.findByUsername(username);
        user.setUrl(blogUrl);
        userRepository.save(user);
    }
    public List<BlogResponseDto> findAllBlogsPostedByCurrentUser(String username){
        // get the current user by username
        UserEntity user = userRepository.findByUsername(username);
        // list of blogs of the current user
        List<BlogEntity> blogs = user.getBlogList();
        // convert it into list of request dtos
        List<BlogResponseDto> blogResponseDtos = new ArrayList<>();
        for(BlogEntity blog : blogs){
            BlogResponseDto blogResponseDto = new BlogResponseDto();

            blogResponseDto.setTitle(blog.getTitle());
            // convert image url to image
            String contentWithImage = replaceImageUrls(blog.getContents());
            blogResponseDto.setContents(contentWithImage);
            String date = dateConvtToString(blog.getPubDate());
            blogResponseDto.setPubDate(date);
            blogResponseDto.setDescription(blog.getDescription());
            blogResponseDtos.add(blogResponseDto);
        }
        return blogResponseDtos;
    }
    public String dateConvtToString(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd");
        return formatter.format(date);
    }
    public BlogResponseDto viewBlogById(long blogId){
        BlogEntity blog;
        try {
            blog = blogRepository.findById(blogId).get();
        }catch (Exception b){
            throw new BlogNotFound("Blog not found!!");
        }
        String contentWithImage = replaceImageUrls(blog.getContents());
        System.out.println(contentWithImage);
        BlogResponseDto blogResponseDto = new BlogResponseDto();
        blogResponseDto.setContents(contentWithImage);
        blogResponseDto.setTitle(blog.getTitle());
        // convert sql date format to a pattern
        String date = dateConvtToString(blog.getPubDate());
        blogResponseDto.setPubDate(date);
        return blogResponseDto;
    }

//    public List<String> imageUrlExtractor(String content){
//        Matcher matcher = IMAGE_URL_PATTERN.matcher(content);
//        List<String> urls = new ArrayList<>();
//        while (matcher.find()) {
//            String imageUrl = matcher.group(1);
//            urls.add(imageUrl);
//        }
//        return urls;
//    }
    public String replaceImageUrls(String content){
        Matcher matcher = IMAGE_URL_PATTERN.matcher(content);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String imageUrl = matcher.group(1);
            String imageHeight = "100";
            String imgTag = "<img src=\"" + imageUrl + "\" height=\"" + imageHeight + "\" />";
            String divTag = "<div class=\"post-image-container\">" + imgTag + "</div>";
            matcher.appendReplacement(sb, divTag);
        }
        matcher.appendTail(sb);
        Safelist safelist = Safelist.none()
                .addTags("img","div")
                .addAttributes("img", "src", "alt", "height")
                .addAttributes("div","class");

        return Jsoup.clean(sb.toString(),safelist);
    }

}
