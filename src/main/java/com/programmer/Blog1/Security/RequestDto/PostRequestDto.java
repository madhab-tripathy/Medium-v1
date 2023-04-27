package com.programmer.Blog1.Security.RequestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
    private long id;
    private String title;
    private String contents;
    private String description;
}
