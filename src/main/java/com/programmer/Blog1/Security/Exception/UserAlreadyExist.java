package com.programmer.Blog1.Security.Exception;

import java.io.Serial;

public class UserAlreadyExist extends RuntimeException{
    public UserAlreadyExist (String message)
    {
        super(message);
    }
}
