package com.findme.service.userService.CheckDeleteRelationship;

import com.findme.dao.userDAO.IUserDAODeleteRelationship;
import com.findme.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class is a description for the validation pattern chain of responsibility part.
 * This part of the check, checks to remove the user, you need friends with him for 3 days or more.
 */
@Component(value = "relationshipTime")
public class RelationshipTime implements IDeleteRelationship {
    private IDeleteRelationship next;
    private IUserDAODeleteRelationship iUserDAODeleteRelationship;

    /**
     * DI for iUserDAODeleteRelationship
     *
     * @param iUserDAODeleteRelationship
     */
    @Autowired
    public RelationshipTime(IUserDAODeleteRelationship iUserDAODeleteRelationship) {
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
     * This method verifies that the user will be deleted if they have been friends for 3 days or more.
     *
     * @param session    - user who at now logged in at the session
     * @param userIdFrom - user who want deleted
     * @param userIdTo   - user whom want deleted
     */
    @Override
    public void delete(HttpSession session, String userIdFrom, String userIdTo) {
        if (countingDays(iUserDAODeleteRelationship.relationshipTime(userIdFrom, userIdTo)) <= 3)
            throw new BadRequestException("You can't delete user: " + userIdTo +
                    " because have you known for less than 3 days");
        else checkNull(session, userIdFrom, userIdTo);
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

    /**
     * This method counts how many days have passed since you added as a friend.
     * Firstly we create a class object SimpleDateFormate, then to change the format of the input dates.
     * Secondly, it counts the number of days.
     *
     * @param dateBefore - date when added as a friends.
     * @return number of days from the moment of adding.
     */
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
