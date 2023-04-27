package com.programmer.Blog1.Blogger.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeBlogResponseDto {
    private BlogResponseDto blogResponseDto;
    private String name;
}
