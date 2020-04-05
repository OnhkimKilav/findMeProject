package com.findme.service.userService;

import com.findme.dao.ICRUDDAO;
import com.findme.dao.userDAO.IUserDAOGetUser;
import com.findme.exception.BadRequestException;
import com.findme.model.User;
import com.findme.service.IServiceCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import java.util.Date;

/**
 * This class is a functional description for CRUD
 */

@org.springframework.stereotype.Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserServiceCRUDImpl implements IServiceCRUD<User> {
    private ICRUDDAO<User> icruddao;
    private IUserDAOGetUser userDAOGetUser;

    @Autowired
    public UserServiceCRUDImpl(ICRUDDAO<User> icruddao, IUserDAOGetUser userDAOGetUser) {
        this.icruddao = icruddao;
        this.userDAOGetUser = userDAOGetUser;
    }

    /**
     * This method implements a check to save the user in the database. To add a user to the database,
     * you must pass the test. That email and password are individual and not used in the database by other users.
     * If a date is not specified during registration, she will be assigned today's date.
     *
     * @param user - user to save
     * @return user if it is saved
     */

    @Override
    public User save(User user){
        if (userDAOGetUser.userByPhone(user.getPhone()) != 0)
            throw new BadRequestException("This phone is already use");
        if (userDAOGetUser.userByEmail(user.getEmail()) != 0)
            throw new BadRequestException("This email is already use");
        if (user.getDateRegistered() == null)
            user.setDateRegistered(new Date());
        if (user.getDateLastActive() == null)
            user.setDateLastActive(new Date());

        return icruddao.save(user);
    }


    @Override
    public void delete(Long id) {
        icruddao.delete(id);
    }

    @Override
    public void update(User user) {
        icruddao.update(user);
    }

    @Override
    public User findById(Long id) {
        return icruddao.findById(id);
    }

}
