package com.findme.check;

import com.findme.exception.BadRequestException;
import com.findme.model.User;

import javax.servlet.http.HttpSession;

/**
 * This class contains a check for the user.
 */
public class checkUser {

    /**
     * The method checks if the user is logged in under himself to call any functionality.
     * Verification consists in obtaining the user ID from the session and comparing with the transmitted user ID.
     * If validation to not passed, returned exception "BadRequestException" with message.
     *
     * @param session    - session which run at the moment
     * @param userIdFrom - identifier of the user who is requesting some function
     */
    public static void userIsSessionUser(HttpSession session, String userIdFrom) {
        if (!(String.valueOf(((User) session.getAttribute("user")).getId()).equals(userIdFrom)))
            throw new BadRequestException("To doing this function you must log in under this user.");
    }

    /**
     * This check is intended for checking both users. Is one of the users logged in to request any functionality.
     * Verification consists in obtaining the user identifier from the session and comparing it with the transmitted
     * user identifiers.
     * If both user id not logging in in the session then returned exception "BadRequestException" with message.
     *
     * @param session    - user who at now logged in at the session
     * @param userIdFrom - ID of the user who sends
     * @param userIdTo   - ID of the user who receives
     */
    public static void bothUserIsSessionUser(HttpSession session, String userIdFrom, String userIdTo) {
        if (!(String.valueOf(((User) session.getAttribute("user")).getId()).equals(userIdFrom)) &&
                !(String.valueOf(((User) session.getAttribute("user")).getId()).equals(userIdTo)))
            throw new BadRequestException("To doing this function you must log in under one of the users.");
    }

    /**
     * This check is intended to check that the user does not use himself to call some functionality.
     * If the identifier of the user who requests any functionality is equal to the identifier of the user to whom this
     * functionality is applied, returns a BadRequestException with a message.
     *
     * @param userIdFrom - ID of the user who sends
     * @param userIdTo   - ID of the user who receives
     */
    public static void userIsYourself(String userIdFrom, String userIdTo) {
        if (userIdFrom.equals(userIdTo))
            throw new BadRequestException("You can't using this function on yourself.");
    }

    /**
     * This check is intended to check both users so that they aren't null.
     *
     * @param userIdFrom - ID of the user who sends
     * @param userIdTo   - ID of the user who receives
     */
    public static void bothUsersNull(String userIdFrom, String userIdTo) {
        if (userIdFrom.equals("null") || userIdTo.equals("null"))
            throw new NullPointerException("You pass a null value.");
    }

    /**
     * This check is intended to check status relationship on a null.
     *
     * @param statusRelationship - status which need a check
     */
    public static void nullStatusRelationship(String statusRelationship) {
        if (statusRelationship == null)
            throw new BadRequestException("You cannot update relationship which does not exist");
    }
}
