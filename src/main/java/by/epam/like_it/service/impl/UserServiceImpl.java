package by.epam.like_it.service.impl;

import by.epam.like_it.dao.DAOFactory;
import by.epam.like_it.dao.QuAnDAO;
import by.epam.like_it.dao.UserDAO;
import by.epam.like_it.entity.Mark;
import by.epam.like_it.entity.User;
import by.epam.like_it.exception.DAOException;
import by.epam.like_it.exception.ServiceException;
import by.epam.like_it.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    @Override
    public User authorizeUser(String login, String password) throws ServiceException {

        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO = factory.getUserDAO();

        User user = null;

        try {

            user = userDAO.authorUser(login, password);

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


    @Override
    public void setUserImage(String imageName, User user) throws ServiceException {

        try {
            DAOFactory.getInstance().getUserDAO().setImageName(imageName,user);
        } catch (DAOException e) {
            throw  new ServiceException(e);
        }
    }

    @Override
    public User getUserById(int id) throws ServiceException {

        try {

            UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
            User user = userDAO.getUserById(id);

            return user;

        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int getUserRate(User user) throws ServiceException {

        QuAnDAO quAnDAO=DAOFactory.getInstance().getQuAnDAO();
        int rate=0;

        try {

            List<Mark> marks=quAnDAO.getMarksByUser(user);

            for(Mark mark:marks){

                if (mark.getType()==Mark.Type.UP){
                    rate++;
                } else if (mark.getType() == Mark.Type.DOWN){
                    rate--;
                }
            }

            return rate;

        } catch (DAOException e) {
           throw new ServiceException(e);
        }
    }
}
