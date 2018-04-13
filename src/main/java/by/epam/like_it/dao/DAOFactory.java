package by.epam.like_it.dao;

import by.epam.like_it.dao.connection_pool.ConnectionPool;
import by.epam.like_it.dao.impl.QuAnDAOImpl;
import by.epam.like_it.dao.impl.UserDAOImpl;


public class DAOFactory {

    private static DAOFactory ourInstance = new DAOFactory();
    private QuAnDAO quAnDAO;
    private UserDAO userDAO;
    private ConnectionPool connectionPool=ConnectionPool.getInstance();

    private DAOFactory() {
        quAnDAO = new QuAnDAOImpl(connectionPool);
        userDAO = new UserDAOImpl(connectionPool);
    }

    public static DAOFactory getInstance() {
        return ourInstance;
    }


    public QuAnDAO getQuAnDAO(){
        return quAnDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

}
