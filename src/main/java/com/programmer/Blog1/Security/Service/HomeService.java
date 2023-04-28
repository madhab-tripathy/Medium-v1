package com.programmer.Blog1.Security.Service;

import com.programmer.Blog1.Security.Model.BlogEntity;
import com.programmer.Blog1.Security.RequestDto.UserLoginDto;
import com.programmer.Blog1.Security.ResponseDto.BlogResponseDto;
import com.programmer.Blog1.Security.ResponseDto.HomeBlogResponseDto;

import java.util.List;

public interface HomeService {
    String updateUserPasswordBeforeLogin(UserLoginDto userLoginDto) throws Exception;
    List<HomeBlogResponseDto> getAllBlogsInHomePage();
    List<HomeBlogResponseDto> getTrendingBlogs();
    BlogResponseDto blogResponseConverter(BlogEntity blog);
    HomeBlogResponseDto homeBlogResponseConverter(BlogResponseDto blogResponseDto, String name);
}
