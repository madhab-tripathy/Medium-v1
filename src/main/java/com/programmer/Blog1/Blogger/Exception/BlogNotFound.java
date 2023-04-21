package com.programmer.Blog1.Blogger.Exception;

public class BlogNotFound extends RuntimeException{
    public BlogNotFound(String message){
        super(message);
    }
}
