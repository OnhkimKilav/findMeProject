package com.findme.service.userService.CheckDeleteRelationship;

import com.findme.RelationshipStatus;
import com.findme.check.checkUser;
import com.findme.dao.userDAO.IUserDAORelationship;
import com.findme.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * This class is the description for the chain of responsibility templates.
 * This part of the check checks to remove the user, you need to go through other checks,
 * such as both users are not null, one of the users is logged in to the session, and they are friends.
 */
@Component(value = "othersChecks")
public class OthersChecks implements IDeleteRelationship {
    private IDeleteRelationship next;
    private IUserDAORelationship userDAORelationship;

    /**
     * DI for userDAORelationship
     *
     * @param userDAORelationship
     */
    @Autowired
    public OthersChecks(IUserDAORelationship userDAORelationship) {
        this.userDAORelationship = userDAORelationship;
    }

    /**
     * This method is responsible for binding the next part of the verification of this pattern.
     *
     * @param next - next class for the verification
     * @return link to the next class to check
     */
    @Override
    public IDeleteRelationship setNext(IDeleteRelationship next) {
        this.next = next;
        return next;
    }

    /**
     * This method checks that the user to be deleted:
     * 1.If both users are not null;
     * 2.If the logged in user is the recipient or sender, I can delete;
     * 3.If both user are friends.
     *
     * @param session    - user who at now logged in at the session
     * @param userIdFrom - user who want deleted
     * @param userIdTo   - user whom want deleted
     */
    @Override
    public void delete(HttpSession session, String userIdFrom, String userIdTo) {
        checkUser.bothUsersNull(userIdFrom, userIdTo);
        checkUser.bothUserIsSessionUser(session, userIdFrom, userIdTo);
        if (!(userDAORelationship.getStatusRelationship(userIdFrom, userIdTo).equals(String.valueOf(RelationshipStatus.FRIENDS)))) {
            throw new BadRequestException("You cannot delete this user. You are not a friends");
        }
        checkNull(session, userIdFrom, userIdTo);
    }

    /**
     * This method checks if the next part is for the template to work.
     * The reference in the next variable is not null, then @delete is called, if null, then the method exits.
     *
     * @param session    - user who at now logged in at the session
     * @param userIdFrom - user who want deleted
     * @param userIdTo   - user whom want deleted
     */
    private void checkNull(HttpSession session, String userIdFrom, String userIdTo) {
        if (next == null)
            return;
        next.delete(session, userIdFrom, userIdTo);
    }
}
