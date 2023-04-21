package com.programmer.Blog1.Blogger.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogResponseDto {
    private String title;
    private String contents;
    private String pubDate;
}
