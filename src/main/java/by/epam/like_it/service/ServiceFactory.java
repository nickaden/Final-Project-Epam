package by.epam.like_it.service;

import by.epam.like_it.service.impl.QuAnServiceImpl;
import by.epam.like_it.service.impl.UserServiceImpl;

public class ServiceFactory {
    private static ServiceFactory ourInstance = new ServiceFactory();
    private QuAnService quAnService=new QuAnServiceImpl();
    private UserService userService=new UserServiceImpl();

    public static ServiceFactory getInstance() {
        return ourInstance;
    }

    public QuAnService getQuAnService(){
        return quAnService;
    }

    public UserService getUserService(){
        return userService;
    }

    private ServiceFactory() {
    }
}
