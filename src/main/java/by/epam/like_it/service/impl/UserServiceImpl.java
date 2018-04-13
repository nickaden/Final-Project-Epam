package by.epam.like_it.service.impl;

import by.epam.like_it.dao.DAOFactory;
import by.epam.like_it.dao.UserDAO;
import by.epam.like_it.entity.User;
import by.epam.like_it.exception.DAOException;
import by.epam.like_it.exception.ServiceException;
import by.epam.like_it.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    @Override
    public User getUser(String login, String password) throws ServiceException {

        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO = factory.getUserDAO();

        User user = null;

        try {

            user = userDAO.getUser(login, password);

        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return user;
    }


    @Override
    public List<User> getUsers() throws ServiceException {

        UserDAO dao = DAOFactory.getInstance().getUserDAO();

        List<User> users = new ArrayList<>();

        try {

            users = dao.getUsers();

        } catch (DAOException e) {
            throw new ServiceException(e);

        }
        return users;
    }


    @Override
    public void editUser(User user) throws ServiceException {

        UserDAO dao = DAOFactory.getInstance().getUserDAO();

        try {

            dao.editUser(user);

        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public int addUser(User user) throws ServiceException {

        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO = factory.getUserDAO();

        try {

            return userDAO.addUser(user);

        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public boolean deleteUser(int userID) throws ServiceException {

        UserDAO dao = DAOFactory.getInstance().getUserDAO();
        boolean isDeleted = false;

        try {

            isDeleted = dao.deleteUser(userID);

        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return isDeleted;
    }


}
