import by.epam.like_it.dao.DAOFactory;
import by.epam.like_it.entity.Tag;
import by.epam.like_it.exception.ServiceException;
import by.epam.like_it.service.QuAnService;
import by.epam.like_it.service.ServiceFactory;
import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class QuAnServiceTest {

    QuAnService quAnService;
    private static final int NOT_VALID_ID=-1;

    @Before
    public void init() throws ClassNotFoundException {
        Class.forName(ServiceFactory.class.getName());
        Class.forName(DAOFactory.class.getName());
        quAnService=ServiceFactory.getInstance().getQuAnService();
    }

    @After
    public void clear(){
        quAnService=null;
    }

    @Test(expected = ServiceException.class)
    public void getAnswerByIdTest() throws ServiceException {
        quAnService.getAnswerById(NOT_VALID_ID);
    }

    @Test(expected = ServiceException.class)
    public void getQuestionsByUserTest() throws ServiceException {
        quAnService.getAnsweredQuestionsByUser(null);
    }

    @Test
    public void deleteTagTest() throws ServiceException {
        assertFalse(quAnService.deleteTag(new Tag()));
    }

    @Test
    public void addTagTest() throws ServiceException {
        assertEquals(NOT_VALID_ID,quAnService.addTag(new Tag()));
    }

}
