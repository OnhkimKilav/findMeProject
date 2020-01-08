package com.findme.service;

import com.findme.dao.UserDAO;
import com.findme.exception.BadRequestException;
import com.findme.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import java.util.Date;

@org.springframework.stereotype.Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService implements Service<User> {
    private UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User save(User user) throws RuntimeException {
        if (userDAO.userByPhone(user.getPhone()) != 0)
            throw new BadRequestException("This phone is already use");
        if (userDAO.userByPhone(user.getEmail()) != 0)
            throw new BadRequestException("This email is already use");
        if (user.getDateRegistered() == null)
            user.setDateRegistered(new Date());
        if (user.getDateLastActive() == null)
            user.setDateLastActive(new Date());

        return userDAO.save(user);
    }

    @Override
    public void delete(Long id) {
        userDAO.delete(id);
    }

    @Override
    public void update(User user) {
        userDAO.update(user);
    }

    @Override
    public User findById(Long id) {
        return userDAO.findById(id);
    }
}
