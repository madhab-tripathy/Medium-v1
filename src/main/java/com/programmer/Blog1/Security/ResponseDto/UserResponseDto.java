package com.programmer.Blog1.Security.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {
    private String name;
    private String username;
    private String email;
    private String mobile;
    private String password;
    private String role;
    private String url;
}
