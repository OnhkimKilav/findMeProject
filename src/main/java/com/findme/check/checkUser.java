package com.findme.check;

import com.findme.exception.BadRequestException;
import com.findme.model.User;

import javax.servlet.http.HttpSession;

public class checkUser {
    public static void userIsSessionUser(HttpSession session, String userIdFrom){
        if(!(String.valueOf(((User) session.getAttribute("user")).getId()).equals(userIdFrom)))
            throw new BadRequestException("To doing this function you must log in under this user.");
    }

    public static void bothUserIsSessionUser(HttpSession session, String userIdFrom, String userIdTo){
        if(!(String.valueOf(((User) session.getAttribute("user")).getId()).equals(userIdFrom)) &&
                !(String.valueOf(((User) session.getAttribute("user")).getId()).equals(userIdTo)))
            throw new BadRequestException("To doing this function you must log in under one of the users.");
    }

    public static void userIsYourself(String userIdFrom, String userIdTo){
        if(userIdFrom.equals(userIdTo))
            throw new BadRequestException("You can't using this function on yourself.");
    }

    public static void bothUsersNull(String userIdFrom, String userIdTo){
        if(userIdFrom.equals("null") || userIdTo.equals("null"))
            throw new NullPointerException("You pass a null value.");
    }
}
