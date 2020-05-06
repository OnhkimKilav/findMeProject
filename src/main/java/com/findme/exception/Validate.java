package com.findme.exception;

import javax.servlet.http.HttpSession;

public class Validate {
    static public void checkLogIn(HttpSession session){
        if (session.getAttribute("user") == null)
            throw new BadRequestException("You are not logged in");
    }
}
