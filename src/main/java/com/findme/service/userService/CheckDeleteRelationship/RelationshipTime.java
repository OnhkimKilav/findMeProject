package com.findme.service.userService.CheckDeleteRelationship;

import com.findme.dao.userDAO.IUserDAODeleteRelationshipCheck;
import com.findme.dao.userDAO.UserDAODeleteRelationshipCheckImpl;
import com.findme.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

/*@org.springframework.stereotype.Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)*/
/*@Component
public class RelationshipTime extends UserDeleteRelationshipCheck{

    @Override
    public boolean deleteRelationship(HttpSession session, String userIdFrom, String userIdTo) {
        System.out.println("Method deleteRalationship from class RelationshipTime is done");
        long countingDays = countingDays(String.valueOf(super.iUserDAODeleteRelationshipCheck.relationshipTime(userIdFrom, userIdTo)));

        if(userIdFrom == null && userIdTo == null){
            throw new NullPointerException("You must indicates userIdFrom or userIdTo");
        }else if(countingDays < 3){
            throw new BadRequestException("You can't delete user: " + userIdTo + " because 3 days have not passed");
        }

        //int days = Days.daysBetween(date1, date2).getDays();
        System.out.println("Next check");
        return checkNext(session, userIdFrom, userIdTo);
    }

    private long countingDays(String dateBefore){
        String d1 = dateBefore;
        String d2 = String.valueOf(new Date());
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = format.parse(d1);
            date2 = format.parse(d2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long difference = date1.getTime() - date2.getTime();
        long days =  difference / (24 * 60 * 60 * 1000);
        return days;
    }
}*/
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RelationshipTime implements IUserDeleteRelationshipCheck {
    private IUserDAODeleteRelationshipCheck iUserDAODeleteRelationshipCheck;

    @Autowired
    public RelationshipTime(IUserDAODeleteRelationshipCheck iUserDAODeleteRelationshipCheck) {
        this.iUserDAODeleteRelationshipCheck = iUserDAODeleteRelationshipCheck;
    }

    private IUserDeleteRelationshipCheck next;

    @Override
    public void setNext(IUserDeleteRelationshipCheck next) {
        this.next = next;
    }

    @Override
    public void deleteRelationship(HttpSession session, String userIdFrom, String userIdTo) {
        System.out.println("Method deleteRalationship from class RelationshipTime is done");
        long countingDays = countingDays(String.valueOf(iUserDAODeleteRelationshipCheck.relationshipTime(userIdFrom, userIdTo)));

        if (userIdFrom == null && userIdTo == null) {
            throw new NullPointerException("You must indicates userIdFrom or userIdTo");
        } else if (countingDays < 3) {
            throw new BadRequestException("You can't delete user: " + userIdTo + " because 3 days have not passed");
        } else {
            System.out.println("Next check");
            next.deleteRelationship(session, userIdFrom, userIdTo);
        }
    }

    private long countingDays(String dateBefore) {
        String d1 = dateBefore;
        String d2 = String.valueOf(new Date());
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = format.parse(d1);
            date2 = format.parse(d2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long difference = date1.getTime() - date2.getTime();
        long days = difference / (24 * 60 * 60 * 1000);
        return days;
    }
}
