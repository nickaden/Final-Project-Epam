import by.epam.like_it.dao.DAOFactory;
import by.epam.like_it.dao.UserDAO;
import by.epam.like_it.entity.User;
import by.epam.like_it.exception.DAOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class UserDAOTest {

    private UserDAO userDAO;
    private static final String EMPTY_STRING="";
    private static final String EXISTING_LOGIN="admin";
    private User existingUser;

    @Before
    public void init() throws ClassNotFoundException {
        Class.forName(DAOFactory.class.getName());
        userDAO=DAOFactory.getInstance().getUserDAO();
        existingUser =new User();
        existingUser.setLogin(EXISTING_LOGIN);
    }

    @After
    public void clear(){
        userDAO=null;
    }

    @Test
    public void authorUserTest() throws DAOException {
        assertNull(userDAO.authorUser(EMPTY_STRING,EMPTY_STRING));
    }

    @Test
    public void getUsersTest() throws DAOException {
        assertNotNull(userDAO.getUsers());
    }

    @Test
    public void addUserTest() throws DAOException {
        assertEquals(-1,userDAO.addUser(existingUser),0.0001);
    }
}
