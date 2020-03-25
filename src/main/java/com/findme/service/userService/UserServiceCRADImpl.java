package com.findme.service.userService;

import com.findme.dao.ICRUDDAO;
import com.findme.dao.userDAO.IUserDAOGetUser;
import com.findme.exception.BadRequestException;
import com.findme.model.User;
import com.findme.service.IServiceCRAD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import java.util.Date;
import java.util.logging.Logger;

@org.springframework.stereotype.Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserServiceCRADImpl implements IServiceCRAD<User> {
    private static Logger logger = Logger.getLogger(UserServiceCRADImpl.class.getName());
    private ICRUDDAO<User> icruddao;
    private IUserDAOGetUser userDAOGetUser;

    @Autowired
    public UserServiceCRADImpl(ICRUDDAO<User> icruddao, IUserDAOGetUser userDAOGetUser) {
        this.icruddao = icruddao;
        this.userDAOGetUser = userDAOGetUser;
    }

    @Override
    public User save(User user) throws RuntimeException {
        if (userDAOGetUser.userByPhone(user.getPhone()) != 0)
            throw new BadRequestException("This phone is already use");
        if (userDAOGetUser.userByEmail(user.getEmail()) != 0)
            throw new BadRequestException("This email is already use");
        if (user.getDateRegistered() == null)
            user.setDateRegistered(new Date());
        if (user.getDateLastActive() == null)
            user.setDateLastActive(new Date());

        return (User) icruddao.save(user);
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
        return (User) icruddao.findById(id);
    }

}
