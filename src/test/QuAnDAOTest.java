import by.epam.like_it.dao.DAOFactory;
import by.epam.like_it.dao.QuAnDAO;
import by.epam.like_it.dao.connection_pool.ConnectionPool;
import by.epam.like_it.dao.connection_pool.ConnectionPoolException;
import by.epam.like_it.exception.DAOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class QuAnDAOTest {
    private QuAnDAO quAnDAO;
    private String RU_LANG=null;
    private static final int NOT_VALID_ID=-1;

    @Before
    public void init() throws ClassNotFoundException {
        Class.forName(DAOFactory.class.getName());
        quAnDAO=DAOFactory.getInstance().getQuAnDAO();
    }

    @After
    public void clear(){
        quAnDAO=null;
    }

    @Test
    public void getQuestionsTest() throws DAOException {
        assertNotNull(quAnDAO.getQuestions(RU_LANG,1));
    }

    @Test
    public void deleteAnswer() throws DAOException {
        assertFalse(quAnDAO.deleteAnswer(NOT_VALID_ID));
    }

    @Test
    public void getQuestionByIdTest() throws DAOException {
        assertNull(quAnDAO.getQuestionById(NOT_VALID_ID));
    }
}
