package by.epam.like_it.service;

import by.epam.like_it.entity.User;
import by.epam.like_it.exception.DAOException;
import by.epam.like_it.exception.ServiceException;

import java.util.List;

public interface UserService {

    User authorizeUser(String login, String password) throws  ServiceException;

    User getUserById(int id) throws ServiceException;

    List<User> getUsers() throws ServiceException;

    int getUserRate(User user) throws ServiceException;

    void editUser(User user) throws ServiceException;

    boolean deleteUser(int userID) throws ServiceException;

    int addUser(User user) throws ServiceException;

    void setUserImage(String imageName, User user) throws ServiceException;
}
