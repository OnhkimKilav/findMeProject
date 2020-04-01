package com.findme.service.userService.CheckDeleteRelationship;

import com.findme.dao.userDAO.IUserDAODeleteRelationship;
import com.findme.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component(value = "relationshipTime")
public class RelationshipTime implements IDeleteRelationship {
    private IDeleteRelationship next;
    private IUserDAODeleteRelationship iUserDAODeleteRelationship;

    @Autowired
    public RelationshipTime(IUserDAODeleteRelationship iUserDAODeleteRelationship) {
        this.iUserDAODeleteRelationship = iUserDAODeleteRelationship;
    }

    @Override
    public IDeleteRelationship setNext(IDeleteRelationship next) {
        this.next = next;
        return next;
    }

    @Override
    public void delete(HttpSession session, String userIdFrom, String userIdTo) {
        if (countingDays(iUserDAODeleteRelationship.relationshipTime(userIdFrom, userIdTo)) < 3)
            throw new BadRequestException("You can't delete user: " + userIdTo + " because 3 days have not passed");
        else checkNull(session, userIdFrom, userIdTo);
    }

    private void checkNull(HttpSession session, String userIdFrom, String userIdTo) {
        if (next == null)
            return;
        next.delete(session, userIdFrom, userIdTo);
    }

    private long countingDays(Date dateBefore) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String date1 = format.format(dateBefore);
        String date2 = format.format(new Date());
        Date date3 = null;
        Date date4 = null;
        try {
            date3 = format.parse(date1);
            date4 = format.parse(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long difference = date4.getTime() - date3.getTime();
        long days = difference / (24 * 60 * 60 * 1000);
        return days;
    }
}
