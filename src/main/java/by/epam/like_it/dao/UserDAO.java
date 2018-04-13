package by.epam.like_it.dao;

import by.epam.like_it.entity.User;
import by.epam.like_it.exception.DAOException;

import java.util.List;

public interface UserDAO {

    User getUser(String login, String password) throws DAOException;

    List<User> getUsers() throws DAOException;

    void editUser(User user) throws DAOException;

    int addUser(User user) throws DAOException;

    boolean deleteUser(int userID) throws DAOException;

    void setImageName(String name, User user) throws DAOException;
}
