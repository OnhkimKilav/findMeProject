package com.findme.service.userService.CheckDeleteRelationship;

import com.findme.dao.userDAO.IUserDAODeleteRelationship;
import com.findme.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component(value = "maxCountOutgoingRequests")
public class MaxCountOutgoingRequests implements IDeleteRelationship {
    private IDeleteRelationship next;
    private IUserDAODeleteRelationship iUserDAODeleteRelationship;

    @Autowired
    public MaxCountOutgoingRequests(IUserDAODeleteRelationship iUserDAODeleteRelationship) {
        this.iUserDAODeleteRelationship = iUserDAODeleteRelationship;
    }

    @Override
    public IDeleteRelationship setNext(IDeleteRelationship next) {
        this.next = next;
        return next;
    }

    @Override
    public void delete(HttpSession session, String userIdFrom, String userIdTo) {
        if (iUserDAODeleteRelationship.maxCountOutgoingRequest(userIdFrom, userIdTo) > 10)
            throw new BadRequestException("Sorry. You have more than 100 friends.");
        else checkNull(session, userIdTo, userIdFrom);
    }

    private void checkNull(HttpSession session, String userIdFrom, String userIdTo) {
        if (next == null)
            return;
        next.delete(session, userIdFrom, userIdTo);
    }
}
