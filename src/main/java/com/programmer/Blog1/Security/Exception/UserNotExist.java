package com.programmer.Blog1.Security.Exception;

public class UserNotExist extends RuntimeException{
    public UserNotExist(String message){
        super(message);
    }
}
