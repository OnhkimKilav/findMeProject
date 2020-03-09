package com.findme.dao.userDAO;

public interface IUserDAOGetUser {
    Integer userByPhone(String phone);

    Integer userByEmail(String email);
}
