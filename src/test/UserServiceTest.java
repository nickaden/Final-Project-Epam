import by.epam.like_it.dao.DAOFactory;
import by.epam.like_it.entity.User;
import by.epam.like_it.exception.ServiceException;
import by.epam.like_it.service.ServiceFactory;
import by.epam.like_it.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class UserServiceTest {

    private UserService userService;
    private static final int NOT_VALID_ID=-1;
    private int NONE_RATE=0;

    @Before
    public void init() throws ClassNotFoundException {
        Class.forName(ServiceFactory.class.getName());
        Class.forName(DAOFactory.class.getName());
        userService=ServiceFactory.getInstance().getUserService();
    }

    @After
    public void clear() {
        userService=null;
    }

    @Test
    public void getUserRateTest() throws ServiceException {
        assertEquals(NONE_RATE,userService.getUserRate(new User()));
    }

    @Test(expected = ServiceException.class)
    public void getUserByIdTest() throws ServiceException {
        userService.getUserById(NOT_VALID_ID);
    }
}
