package com.programmer.Blog1.Security.Exception;

public class BlogNotFound extends RuntimeException{
    public BlogNotFound(String message){
        super(message);
    }
}
