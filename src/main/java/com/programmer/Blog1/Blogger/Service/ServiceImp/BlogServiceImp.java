package com.programmer.Blog1.Blogger.Service.ServiceImp;

import com.programmer.Blog1.Blogger.Exception.BlogNotFound;
import com.programmer.Blog1.Blogger.Model.BlogEntity;
import com.programmer.Blog1.Blogger.Repository.BlogRepository;
import com.programmer.Blog1.Blogger.RequestDto.PostRequestDto;
import com.programmer.Blog1.Blogger.ResponseDto.BlogResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
@Service
public class BlogServiceImp {
    @Autowired
    private BlogRepository blogRepository;
    private final Pattern IMAGE_URL_PATTERN = Pattern.compile("(?i)\\b(https?://\\S+\\.(?:jpg|jpeg|png)\\S*)\\b");
    public void createBlogPost(PostRequestDto postRequestDto){
        BlogEntity blog = new BlogEntity();
        String title = postRequestDto.getTitle();
        String content = postRequestDto.getContents();
        if(title.isBlank()){
            throw new NullPointerException("Must Contain a title");
        }
        if (content.isBlank()){
            throw new NullPointerException("Must Contain a description");
        }
        blog.setContents(content);
        blog.setTitle(title);
        blogRepository.save(blog);
    }
    public BlogResponseDto viewBlogById(int blogId){
        BlogEntity blog;
        try {
            blog = blogRepository.findById(blogId).get();
        }catch (BlogNotFound b){
            throw new BlogNotFound("Blog not found!!");
        }
        String blogContent = blog.getContents();

        // replace image url with img tag
        String contentWithImage = replaceImageUrls(blogContent);
        BlogResponseDto blogResponseDto = new BlogResponseDto();
        blogResponseDto.setContents(contentWithImage);
        blogResponseDto.setTitle(blog.getTitle());
        // convert sql date format to a pattern
        SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
        String strDate = formatter.format(blog.getPubDate());
        blogResponseDto.setPubDate(strDate);
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

        Document document = Jsoup.parse(content);
        Matcher matcher = IMAGE_URL_PATTERN.matcher(content);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String imageUrl = matcher.group(1);
            String imageHeight = "100";
            String imgTag = "<img src=\"" + imageUrl + "\" height=\"" + imageHeight + "\" alt=\"img\" />";
            matcher.appendReplacement(sb, imgTag);
        }
        matcher.appendTail(sb);

        // Remove any other HTML tags from the document
        Pattern imgPattern = Pattern.compile("<(/?)(?!img)[^>]+?>");
        Matcher imgMatcher = imgPattern.matcher(document.html());
        document.html(imgMatcher.replaceAll(""));

        return document.html();
    }
}
