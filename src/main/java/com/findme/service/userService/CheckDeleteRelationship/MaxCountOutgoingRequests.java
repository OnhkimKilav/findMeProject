package com.findme.service.userService.CheckDeleteRelationship;

import com.findme.dao.userDAO.IUserDAODeleteRelationship;
import com.findme.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * This class is a description for the validation pattern chain of responsibility part.
 * This part of the check, checks to remove the user, you need to have less than 10 outgoing requests in friends.
 */
@Component(value = "maxCountOutgoingRequests")
public class MaxCountOutgoingRequests implements IDeleteRelationship {
    private IDeleteRelationship next;
    private IUserDAODeleteRelationship iUserDAODeleteRelationship;

    /**
     * DI for iUserDAODeleteRelationship
     *
     * @param iUserDAODeleteRelationship
     */
    @Autowired
    public MaxCountOutgoingRequests(IUserDAODeleteRelationship iUserDAODeleteRelationship) {
        this.iUserDAODeleteRelationship = iUserDAODeleteRelationship;
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
     * This method checks that the user to be deleted has less than 10 outgoing requests in friends.
     *
     * @param session    - user who at now logged in at the session
     * @param userIdFrom - user who want deleted
     * @param userIdTo   - user whom want deleted
     */
    @Override
    public void delete(HttpSession session, String userIdFrom, String userIdTo) {
        if (iUserDAODeleteRelationship.maxCountOutgoingRequest(userIdFrom) > 10)
            throw new BadRequestException("Sorry. You have more than 10 outgoing requests in friends");
        else checkNull(session, userIdTo, userIdFrom);
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
