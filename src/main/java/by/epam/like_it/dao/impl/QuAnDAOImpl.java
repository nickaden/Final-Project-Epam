package by.epam.like_it.dao.impl;

import by.epam.like_it.dao.QuAnDAO;
import by.epam.like_it.dao.connection_pool.ConnectionPool;
import by.epam.like_it.dao.connection_pool.ConnectionPoolException;
import by.epam.like_it.entity.*;
import by.epam.like_it.exception.DAOException;

import java.sql.*;
import java.util.*;

public class QuAnDAOImpl implements QuAnDAO {

    private ConnectionPool connectionPool;

    //**** Statements ****//
    private static final String GET_QUESTIONS = "SELECT * FROM question INNER JOIN language ON language.id=question.lang_id WHERE language.title=?";
    private static final String GET_QUESTIONS_BY_USER = "SELECT * FROM question WHERE owner_id=?";
    private static final String GET_QUESTIONS_BY_TAG = "SELECT * FROM question INNER JOIN question_tag ON question.id = question_tag.q_id WHERE question_tag.tag_id=?";
    private static final String GET_QUESTION_BY_ID = "SELECT * FROM question WHERE id=?";
    private static final String GET_QUESTION_ID = "SELECT id FROM question WHERE title=?";
    private static final String GET_QUESTION_BY_ANSWER = "SELECT * FROM question INNER JOIN answer ON question.id=answer.q_id WHERE answer.q_id=?";

    private static final String GET_ANSWERS_BY_QUESTION = "SELECT * FROM answer WHERE q_id=?";
    private static final String GET_ANSWERS_BY_USER = "SELECT * FROM answer WHERE owner_id=?";
    private static final String GET_ANSWER_BY_ID = "SELECT * FROM answer WHERE id=?";

    private static final String GET_MARKS_BY_QUESTION = "SELECT * FROM q_mark WHERE q_id=?";
    private static final String GET_MARKS_BY_ANSWER = "SELECT * FROM an_mark WHERE an_id=?";
    private static final String GET_QUESTION_MARKS_BY_USER="SELECT * FROM q_mark WHERE owner_id=?";
    private static final String GET_ANSWER_MARKS_BY_USER="SELECT * FROM an_mark WHERE owner_id=?";

    private static final String GET_QUESTION_MARK="SELECT * FROM q_mark WHERE q_id=? AND owner_id=?";
    private static final String GET_ANSWER_MARK="SELECT * FROM an_mark WHERE an_id=? AND owner_id=?";

    private static final String GET_SOLUTION_BY_QUESTION = "SELECT * FROM solution WHERE q_id=?";
    private static final String GET_SOLUTION_BY_ANSWER = "SELECT * FROM solution WHERE an_id=?";

    private static final String GET_OWNER_BY_QUESTION = "SELECT * FROM user INNER JOIN question ON question.owner_id=user.id WHERE question.id=?";
    private static final String GET_OWNER_BY_ANSWER = "SELECT * FROM user INNER JOIN answer ON answer.owner_id=user.id WHERE answer.id=?";

    private static final String GET_TAGS_BY_QUESTION = "SELECT * FROM tag INNER JOIN question_tag ON tag.id=question_tag.tag_id WHERE question_tag.q_id=?";
    private static final String GET_TAGS = "SELECT * FROM tag";
    private static final String UPDATE_TAG = "UPDATE tag SET title=? WHERE id=?";
    private static final String DELETE_TAG = "DELETE FROM tag WHERE id=?";

    private static final String GET_LANGUAGE_ID = "SELECT id  FROM language WHERE title=?";

    private static final String ADD_QUESTION = "INSERT INTO question(lang_id, owner_id,title,description,cr_date) VALUES(?, ?, ?, ?,CURRENT_DATE)";
    private static final String EDIT_QUESTION = "UPDATE question SET title=?, description=? WHERE id=?";
    private static final String DELETE_QUESTION = "DELETE FROM question WHERE id=?";

    private static final String ADD_ANSWER = "INSERT INTO answer(owner_id, q_id, cr_date, description) VALUES(?, ?, CURRENT_DATE, ?)";
    private static final String UPDATE_ANSWER = "UPDATE answer SET description=? WHERE id=?";
    private static final String DELETE_ANSWER = "DELETE FROM answer WHERE id=?";

    private static final String ADD_QUESTION_MARK = "INSERT INTO q_mark (owner_id,q_id,type) VALUES(?, ?, ?) ON DUPLICATE KEY UPDATE type=?";
    private static final String ADD_ANSWER_MARK = "INSERT INTO an_mark (owner_id,an_id,type) VALUES(?, ?, ?) ON DUPLICATE KEY UPDATE type=?";
    private static final String ADD_TAG = "INSERT INTO tag (title) VALUES(?) ON DUPLICATE KEY UPDATE title=?";
    private static final String DELETE_TAGS_BY_QUESTION = "DELETE FROM question_tag WHERE q_id=?";
    private static final String GET_TAG_ID = "SELECT id FROM tag WHERE title=?";
    private static final String ADD_QUESTION_TAG = "INSERT INTO question_tag (q_id, tag_id) VALUES(?, ?)";

    private static final String SET_SOLUTION = "INSERT INTO solution (q_id,an_id) VALUES (?, ?) ON DUPLICATE KEY UPDATE an_id=?";
    private static final String DELETE_SOLUTION="DELETE FROM solution where an_id=?";


    //**** Literals ****//
    private static final String ID_KEY = "id";
    private static final String TITLE_KEY = "title";
    private static final String DESCRIPTION_KEY = "description";
    private static final String CR_DATE_KEY = "cr_date";
    private static final String QUESTION_ID_KEY = "q_id";
    private static final String ANSWER_ID_KEY = "an_id";
    private static final String OWNER_ID_KEY = "owner_id";
    private static final String TYPE_KEY = "type";
    private static final String LOGIN_KEY = "login";
    private static final String PASSWORD_KEY = "password";
    private static final String REG_DATE_KEY = "reg_date";
    private static final String NAME_KEY="name";
    private static final String SURNAME_KEY="surname";
    private static final String EMAIL_KEY="email";
    private static final String IMAGE_NAME="image_name";
    private static final String SQL_EXCEPTION = "Database exception";
    private static final String QUESTION_KEY="question";
    private static final String ANSWER_KEY="answer";



    //**** Indexes ****//
    private static final int ID_INDEX = 1;
    private static final int MARK_OWNER_INDEX=2;
    private static final int OWNER_ID_INDEX = 1;
    private static final int QUESTION_ID_INDEX = 2;
    private static final int ANSWER_ID_INDEX = 2;
    private static final int TYPE_INDEX = 3;
    private static final int TYPE_UPDATE_INDEX = 4;
    private static final int QUESTION_NOT_FOUND = -1;
    private static final int SOLUTION_Q_ID = 1;
    private static final int SOLUTION_AN_ID = 2;
    private static final int UPDATE_AN_ID = 3;
    private static final int UPDATE_Q_TITLE = 1;
    private static final int UPDATE_Q_DESCRIPTION = 2;
    private static final int UPDATE_Q_ID = 3;
    private static final int ANSWER_DESCRIPTION_INDEX = 1;


    public QuAnDAOImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }


    @Override
    public List<Question> getQuestions(String lang) throws DAOException {

        List<Question> questions = new ArrayList<>();


        try (Connection connection = connectionPool.takeConnection()) {

            PreparedStatement statement = connection.prepareStatement(GET_QUESTIONS);
            statement.setString(1, lang);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Question question = new Question();
                question.setId(rs.getInt(ID_KEY));
                question.setTitle(rs.getString(TITLE_KEY));
                question.setDescription(rs.getString(DESCRIPTION_KEY));
                question.setCreatingDate(rs.getDate(CR_DATE_KEY));
                question.setAnswered(searchSolution(connection, question.getId(), QUESTION_ID_KEY));
                questions.add(question);
            }

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }

        return questions;
    }


    @Override
    public List<Tag> getTags() throws DAOException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        List<Tag> tags = new ArrayList<>();

        try {

            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(GET_TAGS);
            rs = statement.executeQuery();

            while (rs.next()) {

                Tag tag = new Tag();
                tag.setId(rs.getInt(ID_KEY));
                tag.setTitle(rs.getString(TITLE_KEY));
                tags.add(tag);
            }

        } catch (SQLException | ConnectionPoolException e) {

            throw new DAOException(e);

        } finally {
            connectionPool.closeConnection(connection, statement, rs);
        }

        return tags;
    }


    @Override
    public int addTag(Tag tag) throws DAOException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        int tagID = 0;

        try {

            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(ADD_TAG);
            statement.setString(1, tag.getTitle());
            statement.setString(2, tag.getTitle());
            statement.execute();

            statement = connection.prepareStatement(GET_TAG_ID);
            statement.setString(1, tag.getTitle());

            rs = statement.executeQuery();

            if (rs.next()) {
                tagID = rs.getInt(ID_KEY);
            }

        } catch (SQLException | ConnectionPoolException e) {

            throw new DAOException(e);

        } finally {
            connectionPool.closeConnection(connection, statement, rs);

        }
        return tagID;
    }


    @Override
    public boolean deleteTag(Tag tag) throws DAOException {


        Connection connection = null;
        PreparedStatement statement = null;

        boolean isDeleted = false;

        try {

            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(DELETE_TAG);
            statement.setInt(ID_INDEX, tag.getId());
            statement.execute();

            isDeleted = true;

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);

        } finally {
            connectionPool.closeConnection(connection, statement);

        }

        return isDeleted;
    }




    @Override
    public void editTag(Tag tag) throws DAOException {


        Connection connection = null;
        PreparedStatement statement = null;

        List<Tag> tags = new ArrayList<>();

        try {

            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(UPDATE_TAG);
            statement.setString(1, tag.getTitle());
            statement.setInt(2, tag.getId());

            statement.execute();

        } catch (SQLException | ConnectionPoolException e) {

            throw new DAOException(e);

        } finally {
            connectionPool.closeConnection(connection, statement);
        }

    }




    @Override
    public int addQuestion(Question question, User owner, String lang, List<Tag> tags) throws DAOException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        boolean isAdded = false;
        int questionID = QUESTION_NOT_FOUND;

        try {

            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);

            int langID = getLanguageID(lang);

            statement = connection.prepareStatement(ADD_QUESTION);
            statement.setInt(1, langID);
            statement.setInt(2, owner.getId());
            statement.setString(3, question.getTitle());
            statement.setString(4, question.getDescription());

            statement.execute();

            statement = connection.prepareStatement(GET_QUESTION_ID);
            statement.setString(1, question.getTitle());
            rs = statement.executeQuery();


            if (rs.next()) {
                questionID = rs.getInt(ID_KEY);
                addQuestionTag(connection, tags, questionID);
            } else {
                throw new DAOException();
            }

            connection.commit();

        } catch (SQLException | ConnectionPoolException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new DAOException(e);

        } finally {
            connectionPool.closeConnection(connection, statement, rs);
        }

        return questionID;
    }




    @Override
    public void editQuestion(Question question, List<Tag> tags, int langID) throws DAOException {

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            connection.commit();

            statement = connection.prepareStatement(EDIT_QUESTION);
            statement.setString(UPDATE_Q_TITLE, question.getTitle());
            statement.setString(UPDATE_Q_DESCRIPTION, question.getDescription());
            statement.setInt(UPDATE_Q_ID, question.getId());
            statement.execute();

            statement = connection.prepareStatement(DELETE_TAGS_BY_QUESTION);
            statement.setInt(ID_INDEX, question.getId());
            statement.execute();

            addQuestionTag(connection, tags, question.getId());

            connection.commit();

        } catch (ConnectionPoolException | SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new DAOException(e);

        } finally {
            connectionPool.closeConnection(connection, statement);
        }
    }




    @Override
    public int getLanguageID(String lang) throws DAOException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        int langID = 0;

        try {

            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(GET_LANGUAGE_ID);
            statement.setString(1, lang);
            rs = statement.executeQuery();

            if (rs.next()) {
                langID = rs.getInt(ID_KEY);
            }

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);

        } finally {
            connectionPool.closeConnection(connection, statement, rs);
        }

        return langID;
    }




    @Override
    public boolean deleteQuestion(int id) throws DAOException {

        Connection connection = null;
        PreparedStatement statement = null;
        boolean isDeleted = false;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(DELETE_QUESTION);
            statement.setInt(ID_INDEX, id);
            statement.execute();

            isDeleted = true;

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);

        } finally {
            connectionPool.closeConnection(connection, statement);

        }

        return isDeleted;
    }

    @Override
    public List<Mark> getMarksByQuestion(Question question) throws DAOException{

        List<Mark> marks = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {

            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_MARKS_BY_QUESTION);
            preparedStatement.setInt(ID_INDEX, question.getId());
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Mark mark = new Mark();

                mark.setId(rs.getInt(ID_KEY));
                mark.setOwnerId(rs.getInt(OWNER_ID_KEY));
                mark.setType(Mark.Type.valueOf(rs.getNString(TYPE_KEY)));

                marks.add(mark);
            }

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);

        } finally {
            connectionPool.closeConnection(connection, preparedStatement, rs);
        }

        return marks;
    }

    @Override
    public List<Mark> getMarksByUser(User user) throws DAOException {

        List<Mark> marks = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try{

            connection=connectionPool.takeConnection();
            preparedStatement=connection.prepareStatement(GET_QUESTION_MARKS_BY_USER);
            preparedStatement.setInt(ID_INDEX,user.getId());
            rs=preparedStatement.executeQuery();

            while (rs.next()){
                Mark mark=new Mark();

                mark.setId(rs.getInt(ID_KEY));
                mark.setOwnerId(rs.getInt(OWNER_ID_KEY));
                mark.setType(Mark.Type.valueOf(rs.getString(TYPE_KEY)));

                marks.add(mark);
            }

            preparedStatement=connection.prepareStatement(GET_ANSWER_MARKS_BY_USER);
            preparedStatement.setInt(ID_INDEX,user.getId());
            rs=preparedStatement.executeQuery();

            while (rs.next()){
                Mark mark=new Mark();

                mark.setId(rs.getInt(ID_KEY));
                mark.setOwnerId(rs.getInt(OWNER_ID_KEY));
                mark.setType(Mark.Type.valueOf(rs.getString(TYPE_KEY)));

                marks.add(mark);
            }

            return marks;

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection,preparedStatement,rs);
        }
    }

    @Override
    public List<Mark> getMarksByAnswer(Answer answer) throws DAOException{

        List<Mark> marks = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_MARKS_BY_ANSWER);
            preparedStatement.setInt(ID_INDEX, answer.getId());
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Mark mark = new Mark();

                mark.setId(rs.getInt(ID_KEY));
                mark.setOwnerId(rs.getInt(OWNER_ID_KEY));
                mark.setType(Mark.Type.valueOf(rs.getNString(TYPE_KEY)));

                marks.add(mark);
            }

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);

        } finally {
            connectionPool.closeConnection(connection, preparedStatement, rs);
        }

        return marks;
    }


    @Override
    public List<Tag> getTagsByQuestion(Question question) throws DAOException{

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<Tag> tags = new ArrayList<>();

        try {

            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(GET_TAGS_BY_QUESTION);
            statement.setInt(ID_INDEX, question.getId());
            rs = statement.executeQuery();

            while (rs.next()) {
                Tag tag = new Tag();
                tag.setId(rs.getInt(ID_KEY));
                tag.setTitle(rs.getString(TITLE_KEY));

                tags.add(tag);
            }

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);

        } finally {
            connectionPool.closeConnection(connection, statement, rs);
        }

        return tags;
    }


    @Override
    public User getQuestionOwner(Question question) throws DAOException{

        User owner = null;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {

            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(GET_OWNER_BY_QUESTION);
            statement.setInt(ID_INDEX, question.getId());

            rs = statement.executeQuery();

            if (rs.next()) {

                owner = new User();

                owner.setId(rs.getInt(ID_KEY));
                owner.setLogin(rs.getString(LOGIN_KEY));
                owner.setPassword(rs.getString(PASSWORD_KEY));
                owner.setName(rs.getString(NAME_KEY));
                owner.setSurname(rs.getString(SURNAME_KEY));
                owner.setEmail(rs.getString(EMAIL_KEY));
                owner.setRegDate(rs.getDate(REG_DATE_KEY).toLocalDate());
                owner.setImageName(rs.getString(IMAGE_NAME));

            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);

        } finally {
            connectionPool.closeConnection(connection, statement, rs);
        }

        return owner;
    }


    @Override
    public User getAnswerOwner(Answer answer) throws DAOException{

        User owner = null;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {

            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(GET_OWNER_BY_ANSWER);
            statement.setInt(ID_INDEX, answer.getId());

            rs = statement.executeQuery();

            if (rs.next()) {

                owner = new User();

                owner.setId(rs.getInt(ID_KEY));
                owner.setLogin(rs.getString(LOGIN_KEY));
                owner.setPassword(rs.getString(PASSWORD_KEY));
                owner.setRegDate(rs.getDate(REG_DATE_KEY).toLocalDate());
                owner.setName(rs.getString(NAME_KEY));
                owner.setSurname(rs.getString(SURNAME_KEY));
                owner.setEmail(rs.getString(EMAIL_KEY));
                owner.setImageName(rs.getString(IMAGE_NAME));
            }


        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);

        } finally {
            connectionPool.closeConnection(connection, statement, rs);
        }

        return owner;
    }


    @Override
    public void addAnswer(Answer answer, int questionId) throws DAOException {

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(ADD_ANSWER);
            statement.setInt(1, answer.getOwner().getId());
            statement.setInt(2, questionId);
            statement.setString(3, answer.getDescription());
            statement.execute();

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);

        }

    }


    @Override
    public List<Answer> getAnswersByUser(User user) throws DAOException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        List<Answer> answers = new ArrayList<>();

        try {

            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(GET_ANSWERS_BY_USER);
            statement.setInt(ID_INDEX, user.getId());
            rs = statement.executeQuery();

            while (rs.next()) {

                Answer answer = new Answer();

                answer.setId(rs.getInt(ID_KEY));
                answer.setDescription(rs.getString(DESCRIPTION_KEY));
                answer.setSolution(searchSolution(connection, answer.getId(), ANSWER_ID_KEY));
                answer.setQuestionId(rs.getInt(QUESTION_ID_KEY));
                answer.setOwner(user);
                answer.setCreatingDate(rs.getDate(CR_DATE_KEY));

                answers.add(answer);
            }

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);

        } finally {
            connectionPool.closeConnection(connection, statement, rs);
        }

        return answers;
    }


    @Override
    public List<Question> getQuestionsByUser(User user) throws DAOException{

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<Question> questions = new ArrayList<>();

        try {

            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(GET_QUESTIONS_BY_USER);
            statement.setInt(ID_INDEX, user.getId());
            rs = statement.executeQuery();

            while (rs.next()) {
                Question question = new Question();
                question.setId(rs.getInt(ID_KEY));
                question.setTitle(rs.getString(TITLE_KEY));
                question.setDescription(rs.getString(DESCRIPTION_KEY));
                question.setCreatingDate(rs.getDate(CR_DATE_KEY));
                question.setAnswered(searchSolution(connection, question.getId(), QUESTION_ID_KEY));

                questions.add(question);
            }


        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);

        } finally {
            connectionPool.closeConnection(connection, statement, rs);
        }

        return questions;
    }


    @Override
    public List<Question> getQuestionsByTag(Tag tag) throws DAOException{

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<Question> questions = new ArrayList<>();

        try {

            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(GET_QUESTIONS_BY_TAG);
            statement.setInt(ID_INDEX, tag.getId());
            rs = statement.executeQuery();

            while (rs.next()) {
                Question question = new Question();
                question.setId(rs.getInt(ID_KEY));
                question.setTitle(rs.getString(TITLE_KEY));
                question.setDescription(rs.getString(DESCRIPTION_KEY));
                question.setCreatingDate(rs.getDate(CR_DATE_KEY));
                question.setAnswered(searchSolution(connection, question.getId(), QUESTION_ID_KEY));

                questions.add(question);
            }


        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);

        } finally {
            connectionPool.closeConnection(connection, statement, rs);
        }

        return questions;
    }

    @Override
    public Question getQuestionByAnswer(Answer answer) throws DAOException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        Question question = null;

        try {

            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(GET_QUESTION_BY_ANSWER);
            statement.setInt(ID_INDEX, answer.getQuestionId());
            rs = statement.executeQuery();

            if (rs.next()) {
                question = new Question();

                question.setId(rs.getInt(ID_KEY));
                question.setTitle(rs.getString(TITLE_KEY));
                question.setDescription(rs.getString(DESCRIPTION_KEY));
                question.setAnswered(searchSolution(connection, question.getId(), QUESTION_ID_KEY));
                question.setCreatingDate(rs.getDate(CR_DATE_KEY));
            }

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);

        } finally {
            connectionPool.closeConnection(connection, statement, rs);
        }

        return question;
    }


    @Override
    public void editAnswer(Answer newAnswer) throws DAOException {

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(UPDATE_ANSWER);
            statement.setInt(ANSWER_ID_INDEX, newAnswer.getId());
            statement.setString(ANSWER_DESCRIPTION_INDEX, newAnswer.getDescription());
            statement.execute();

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);

        } finally {
            connectionPool.closeConnection(connection, statement);
        }
    }

    @Override
    public boolean deleteAnswer(int answerId) throws DAOException {

        Connection connection = null;
        PreparedStatement statement = null;
        boolean isDeleted = false;


        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(DELETE_ANSWER);
            statement.setInt(ID_INDEX, answerId);
            int deleted=statement.executeUpdate();

            if (deleted>0) {
                isDeleted = true;
            }

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);

        } finally {
            connectionPool.closeConnection(connection, statement);
        }

        return isDeleted;
    }


    @Override
    public Answer getAnswerById(int id) throws DAOException {

        Answer answer = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(GET_ANSWER_BY_ID);
            statement.setInt(ID_INDEX, id);
            rs = statement.executeQuery();

            if (rs.next()) {
                answer = new Answer();
                answer.setId(rs.getInt(ID_KEY));
                answer.setDescription(rs.getString(DESCRIPTION_KEY));
                answer.setCreatingDate(rs.getDate(CR_DATE_KEY));
                answer.setOwner(getAnswerOwner(answer));
                answer.setQuestionId(rs.getInt(QUESTION_ID_KEY));
                answer.setSolution(searchSolution(connection, answer.getId(), ANSWER_ID_KEY));
                answer.setMarks(getMarksByAnswer(answer));
            }

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);

        } finally {
            connectionPool.closeConnection(connection, statement, rs);
        }

        return answer;
    }

    @Override
    public void setSolution(int questionID, int answerID) throws DAOException{

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs=null;

        try {

            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);

            statement=connection.prepareStatement(GET_SOLUTION_BY_ANSWER);
            statement.setInt(ID_INDEX,answerID);
            rs=statement.executeQuery();

            if(rs.next()){

                statement=connection.prepareStatement(DELETE_SOLUTION);
                statement.setInt(ID_INDEX,answerID);
                statement.execute();

            } else {

                statement = connection.prepareStatement(SET_SOLUTION);
                statement.setInt(SOLUTION_Q_ID, questionID);
                statement.setInt(SOLUTION_AN_ID, answerID);
                statement.setInt(UPDATE_AN_ID, answerID);
                statement.execute();

            }

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection,statement,rs);
        }
    }


    @Override
    public Question getQuestionById(int id) throws DAOException {

        Question question = null;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {

            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(GET_QUESTION_BY_ID);
            statement.setInt(ID_INDEX, id);
            rs = statement.executeQuery();

            if (rs.next()) {
                question = new Question();
                question.setId(id);
                question.setTitle(rs.getString(TITLE_KEY));
                question.setDescription(rs.getString(DESCRIPTION_KEY));
                question.setCreatingDate(rs.getDate(CR_DATE_KEY));
                question.setAnswered(searchSolution(connection, id, QUESTION_ID_KEY));
            }

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);

        } finally {
            connectionPool.closeConnection(connection, statement, rs);

        }

        return question;
    }


    @Override
    public List<Answer> getAnswersByQuestion(Question question) throws DAOException{

        List<Answer> answers = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {

            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(GET_ANSWERS_BY_QUESTION);
            statement.setInt(ID_INDEX, question.getId());
            rs = statement.executeQuery();

            while (rs.next()) {

                Answer answer = new Answer();
                answer.setId(rs.getInt(ID_KEY));
                answer.setCreatingDate(rs.getDate(CR_DATE_KEY));
                answer.setDescription(rs.getString(DESCRIPTION_KEY));
                answer.setSolution(searchSolution(connection, answer.getId(), ANSWER_ID_KEY));

                answers.add(answer);
            }

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);

        } finally {
            connectionPool.closeConnection(connection, statement, rs);
        }

        return answers;
    }


    private boolean searchSolution(Connection connection, int id, String typeOfSearch) throws DAOException{

        String sqlQuery;

        if (typeOfSearch.equals(QUESTION_ID_KEY)) {
            sqlQuery = GET_SOLUTION_BY_QUESTION;
        } else {
            sqlQuery = GET_SOLUTION_BY_ANSWER;
        }

        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)){

            statement.setInt(ID_INDEX, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            throw new DAOException(e);

        }

        return false;
    }


    @Override
    public boolean addQuestionMark(Mark mark, int id) throws DAOException{

        Connection connection = null;
        PreparedStatement statement = null;

        boolean isAdded = false;

        try {

            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(ADD_QUESTION_MARK);
            statement.setInt(OWNER_ID_INDEX, mark.getOwnerId());
            statement.setInt(QUESTION_ID_INDEX, id);
            statement.setString(TYPE_INDEX, mark.getType().toString());
            statement.setString(TYPE_UPDATE_INDEX, mark.getType().toString());
            statement.execute();

            isAdded = true;

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);

        } finally {
            connectionPool.closeConnection(connection, statement);

        }

        return isAdded;
    }


    @Override
    public boolean addAnswerMark(Mark mark, int id) throws DAOException{
        Connection connection = null;
        PreparedStatement statement = null;

        boolean isAdded = false;

        try {

            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(ADD_ANSWER_MARK);
            statement.setInt(OWNER_ID_INDEX, mark.getOwnerId());
            statement.setInt(ANSWER_ID_INDEX, id);
            statement.setString(TYPE_INDEX, mark.getType().toString());
            statement.setString(TYPE_UPDATE_INDEX, mark.getType().toString());
            statement.execute();

            isAdded = true;

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);

        } finally {
            connectionPool.closeConnection(connection, statement);

        }

        return isAdded;
    }

    @Override
    public Mark getMark(String typeOfMark, int id, int ownerId) throws DAOException {

        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet rs=null;
        Mark mark=null;

        try {
            connection=connectionPool.takeConnection();
            String Query=null;

            if (typeOfMark.equals(QUESTION_KEY)){
                Query=GET_QUESTION_MARK;
            } else if (typeOfMark.equals(ANSWER_KEY)) {
                Query=GET_ANSWER_MARK;
            }

            statement=connection.prepareStatement(Query);
            statement.setInt(ID_INDEX,id);
            statement.setInt(MARK_OWNER_INDEX,ownerId);
            rs=statement.executeQuery();

            if (rs.next()){

                mark=new Mark();
                mark.setId(rs.getInt(ID_KEY));
                mark.setType(Mark.Type.valueOf(rs.getString(TYPE_KEY)));
                mark.setOwnerId(rs.getInt(OWNER_ID_KEY));
            }

            return mark;

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection,statement,rs);
        }

    }

    private void addQuestionTag(Connection connection, List<Tag> tagList, int questionID) throws DAOException {

        PreparedStatement statement = null;
        ResultSet rs = null;

        try {

            for (Tag tag : tagList) {

                statement = connection.prepareStatement(ADD_TAG);
                statement.setString(1, tag.getTitle());
                statement.setString(2, tag.getTitle());
                statement.execute();

                statement = connection.prepareStatement(GET_TAG_ID);
                statement.setString(1, tag.getTitle());
                rs = statement.executeQuery();

                if (!rs.next()) {
                    return;
                }
                statement = connection.prepareStatement(ADD_QUESTION_TAG);
                statement.setInt(ID_INDEX, questionID);
                statement.setInt(2, rs.getInt(ID_KEY));
                statement.execute();
            }

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DAOException(e1);
            }
            throw new DAOException(e);
        }
    }


}
