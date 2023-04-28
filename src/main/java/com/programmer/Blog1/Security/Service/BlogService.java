package com.programmer.Blog1.Security.Service;

import com.programmer.Blog1.Security.Model.BlogEntity;
import com.programmer.Blog1.Security.RequestDto.PostRequestDto;
import com.programmer.Blog1.Security.ResponseDto.BlogResponseDto;
import com.programmer.Blog1.Security.ResponseDto.HomeBlogResponseDto;

import java.util.Date;
import java.util.List;

public interface BlogService {
    List<BlogEntity> getAllBlogs();
    BlogEntity getBlogById(long id) throws Exception;
    BlogEntity createBlogPost(String username, PostRequestDto postRequestDto) throws Exception;
    BlogEntity updateBlogPost(long id,PostRequestDto postRequestDto) throws Exception;
    List<BlogResponseDto> findAllBlogsPostedByCurrentUser(String username);
    HomeBlogResponseDto viewBlogs(long id) throws Exception;
    String dateConvtToString(Date date);
    String replaceImageUrls(String content);
    List<BlogEntity> getTopSixBlogs();
    void deletePostById(long id);
}
