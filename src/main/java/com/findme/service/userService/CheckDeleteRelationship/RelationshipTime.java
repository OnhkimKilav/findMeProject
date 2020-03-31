package com.findme.service.userService.CheckDeleteRelationship;

import com.findme.dao.userDAO.IUserDAODeleteRelationship;
import com.findme.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    public void deleteRelationship(String userIdFrom, String userIdTo) {
        System.out.println("Method deleteRalationship from class RelationshipTime is done");
        long countingDays = countingDays(iUserDAODeleteRelationship.relationshipTime(userIdFrom, userIdTo));

        if (userIdFrom == null && userIdTo == null) {
            throw new NullPointerException("You must indicates userIdFrom or userIdTo");
        } else if (countingDays < 3) {
            throw new BadRequestException("You can't delete user: " + userIdTo + " because 3 days have not passed");
        } else {
            System.out.println("Next check");
            checkNull(userIdFrom, userIdTo);
        }
    }

    private void checkNull(String userIdFrom, String userIdTo){
        if(next == null)
            return;
        next.deleteRelationship(userIdFrom, userIdTo);
    }

    private long countingDays(Date dateBefore) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String date3 = format.format(dateBefore);
        String date4 = format.format(new Date());
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = format.parse(date4);
            date2 = format.parse(date3);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long difference = date1.getTime() - date2.getTime();
        long days = difference / (24 * 60 * 60 * 1000);
        return days;
    }
}
