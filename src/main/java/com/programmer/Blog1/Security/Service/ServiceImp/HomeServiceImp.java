package com.programmer.Blog1.Security.Service.ServiceImp;

import com.programmer.Blog1.Security.Model.BlogEntity;
import com.programmer.Blog1.Security.ResponseDto.BlogResponseDto;
import com.programmer.Blog1.Security.ResponseDto.HomeBlogResponseDto;
import com.programmer.Blog1.Security.Model.UserEntity;
import com.programmer.Blog1.Security.Repository.UserRepository;
import com.programmer.Blog1.Security.RequestDto.UserLoginDto;
import com.programmer.Blog1.Security.Service.BlogService;
import com.programmer.Blog1.Security.Service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
@Service
public class HomeServiceImp implements HomeService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncode;
    @Autowired
    private BlogService blogService;
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
    public List<HomeBlogResponseDto> getAllBlogsInHomePage(){
        List<BlogEntity> blogs = blogService.getAllBlogs();
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
        blogResponseDto.setId(blog.getId());
        blogResponseDto.setTitle(blog.getTitle());
        blogResponseDto.setContents(blog.getContents());
        blogResponseDto.setDescription(blog.getDescription());
        String date = blogService.dateConvtToString(blog.getPubDate());
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
        List<BlogEntity> blogs = blogService.getTopSixBlogs();
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
