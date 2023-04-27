package com.programmer.Blog1.Security.Service.ServiceImp;

import com.programmer.Blog1.Security.Model.BlogEntity;
import com.programmer.Blog1.Security.Repository.BlogRepository;
import com.programmer.Blog1.Security.ResponseDto.BlogResponseDto;
import com.programmer.Blog1.Security.ResponseDto.HomeBlogResponseDto;
import com.programmer.Blog1.Security.Model.UserEntity;
import com.programmer.Blog1.Security.Repository.UserRepository;
import com.programmer.Blog1.Security.RequestDto.UserLoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
@Service
public class HomeServiceImp {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncode;
    @Autowired
    private BlogRepository blogRepository1;
    @Autowired
    private BlogServiceImp blogService1;
    public String updateUserPasswordBeforeLogin(UserLoginDto userLoginDto) throws Exception {
        String email = userLoginDto.getEmail();
        String newPass = userLoginDto.getNewPassword();
        if(!email.isBlank() && !newPass.isBlank()){
            UserEntity user = userRepository.findByEmail(userLoginDto.getEmail());
            boolean isOldAndNewPassSame = passwordEncode.matches(newPass,user.getPassword());
            if(!isOldAndNewPassSame){
                user.setPassword(passwordEncode.encode(newPass));
                userRepository.save(user);
                return "Password update successfully...";
            }else {
                throw new Exception("Password must be different from previous password");
            }
        }
        throw new Exception("Invalid inputs!!");
    }
    // dto - HomeBlogResponseDto
    // name, title, description, pubDate
    public List<HomeBlogResponseDto> getAllBlogsInHomePage(){
        List<BlogEntity> blogs = blogRepository1.findAll();
        List<HomeBlogResponseDto> homeBlogResponseDtos = new ArrayList<>();
        for(BlogEntity blog : blogs){
            UserEntity user = blog.getUserEntity();
            BlogResponseDto blogResponseDto = blogResponseConverter(blog);
            HomeBlogResponseDto homeBlogResponseDto = homeBlogResponseConverter(blogResponseDto,user.getName());
            homeBlogResponseDtos.add(homeBlogResponseDto);
        }
        return homeBlogResponseDtos;
    }
    public BlogResponseDto blogResponseConverter(BlogEntity blog){
        BlogResponseDto blogResponseDto = new BlogResponseDto();
        blogResponseDto.setTitle(blog.getTitle());
        blogResponseDto.setContents(blog.getContents());
        blogResponseDto.setDescription(blog.getDescription());
        String date = blogService1.dateConvtToString(blog.getPubDate());
        blogResponseDto.setPubDate(date);
        return blogResponseDto;
    }
    public HomeBlogResponseDto homeBlogResponseConverter(BlogResponseDto blogResponseDto, String name){
        HomeBlogResponseDto homeBlogResponseDto = new HomeBlogResponseDto();
        homeBlogResponseDto.setBlogResponseDto(blogResponseDto);
        homeBlogResponseDto.setName(name);
        return homeBlogResponseDto;
    }
    public List<HomeBlogResponseDto> getTrendingBlogs(){
        List<BlogEntity> blogs = blogRepository1.findTopSixBlogs();
        List<HomeBlogResponseDto> homeBlogResponseDtos = new ArrayList<>();
        for(BlogEntity blog : blogs){
            UserEntity user = blog.getUserEntity();
            BlogResponseDto blogResponseDto = blogResponseConverter(blog);
            HomeBlogResponseDto homeBlogResponseDto = homeBlogResponseConverter(blogResponseDto,user.getName());
            homeBlogResponseDtos.add(homeBlogResponseDto);
        }
        return homeBlogResponseDtos;
    }
}
