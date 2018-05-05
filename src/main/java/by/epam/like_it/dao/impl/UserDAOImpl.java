package by.epam.like_it.dao.impl;


import by.epam.like_it.dao.UserDAO;
import by.epam.like_it.dao.connection_pool.ConnectionPool;
import by.epam.like_it.dao.connection_pool.ConnectionPoolException;
import by.epam.like_it.entity.User;
import by.epam.like_it.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private final ConnectionPool connectionPool;

    //**** Statements ****//
    private static final String GET_USER="SELECT * FROM user WHERE login=? AND password=?";
    private static final String GET_USER_BY_ID="SELECT * FROM user WHERE id=?";
    private static final String GET_USERS="SELECT * FROM user";
    private static final String GET_USER_ID="SELECT id FROM user WHERE login=?";
    private static final String GET_USER_BY_LOGIN="SELECT * FROM user WHERE login=?";


    private static final String ADD_USER="INSERT INTO user (login, password, role, name, surname, email, reg_date, image_name) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_USER="UPDATE user SET login=?, password=?, role=?, name=?, surname=?, email=?, image_name=? WHERE id=?";
    private static final String DELETE_USER="DELETE FROM user WHERE id=?";
    private static final String SET_IMAGE="UPDATE user SET image_name=? WHERE id=?";

    //**** Literals ****//
    private static final String ID_KEY = "id";
    private static final String LOGIN_KEY="login";
    private static final String PASSWORD_KEY="password";
    private static final String NAME_KEY="name";
    private static final String SURNAME_KEY="surname";
    private static final String EMAIL_KEY="email";
    private static final String REG_DATE_KEY="reg_date";
    private static final String ROLE_KEY="role";
    private static final String IMAGE_KEY="image_name";

    //**** Indexes ****//
    private static final int GET_ID_INDEX=1;
    private static final int LOGIN_INDEX=1;
    private static final int PASSWORD_INDEX=2;
    private static final int ROLE_INDEX=3;
    private static final int NAME_INDEX=4;
    private static final int SURNAME_INDEX=5;
    private static final int EMAIL_INDEX=6;
    private static final int REG_DATE_INDEX=7;
    private static final int ID_INDEX=8;
    private static final int FIRST_INDEX=1;
    private static final int SECOND_INDEX=2;
    private static final int IMAGE_NAME_INDEX =7;

    public UserDAOImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }


    @Override
    public User authorUser(String login, String password) throws DAOException {

        User user=null;

        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet rs=null;

        try {
            connection=connectionPool.takeConnection();
            statement=connection.prepareStatement(GET_USER);
            statement.setString(LOGIN_INDEX,login);
            statement.setString(PASSWORD_INDEX,password);
            rs=statement.executeQuery();
            if (rs.next()){
                user=new User();
                user.setId(rs.getInt(ID_KEY));
                user.setLogin(rs.getString(LOGIN_KEY));
                user.setPassword(rs.getString(PASSWORD_KEY));
                user.setRegDate(rs.getDate(REG_DATE_KEY).toLocalDate());
                user.setRole(User.Role.valueOf(rs.getString(ROLE_KEY)));
                user.setImageName(rs.getString(IMAGE_KEY));
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);

        } finally {
            connectionPool.closeConnection(connection,statement,rs);

        }

        return user;
    }


    @Override
    public User getUserById(int id) throws DAOException {

        User user=null;

        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet rs=null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(GET_USER_BY_ID);
            statement.setInt(GET_ID_INDEX,id);
            rs = statement.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt(ID_KEY));
                user.setLogin(rs.getString(LOGIN_KEY));
                user.setPassword(rs.getString(PASSWORD_KEY));
                user.setName(rs.getString(NAME_KEY));
                user.setSurname(rs.getString(SURNAME_KEY));
                user.setEmail(rs.getString(EMAIL_KEY));
                user.setRegDate(rs.getDate(REG_DATE_KEY).toLocalDate());
                user.setRole(User.Role.valueOf(rs.getString(ROLE_KEY)));
                user.setImageName(rs.getString(IMAGE_KEY));
            }

            return user;

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);

        } finally {
            connectionPool.closeConnection(connection,statement,rs);

        }
    }

    @Override
    public List<User> getUsers() throws DAOException {

        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet rs=null;

        List<User> users = new ArrayList<>();

        try{

            connection=connectionPool.takeConnection();
            statement=connection.prepareStatement(GET_USERS);
            rs=statement.executeQuery();

            while (rs.next()) {
                User user=new User();

                user.setId(rs.getInt(ID_KEY));
                user.setLogin(rs.getString(LOGIN_KEY));
                user.setPassword(rs.getString(PASSWORD_KEY));
                user.setName(rs.getNString(NAME_KEY));
                user.setSurname(rs.getNString(SURNAME_KEY));
                user.setEmail(rs.getNString(EMAIL_KEY));
                user.setRegDate(rs.getDate(REG_DATE_KEY).toLocalDate());
                user.setRole(User.Role.valueOf(rs.getString(ROLE_KEY)));
                user.setImageName(rs.getString(IMAGE_KEY));

                users.add(user);
            }


        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);

        } finally {
            connectionPool.closeConnection(connection,statement,rs);
        }

        return users;
    }

    @Override
    public int addUser(User user) throws DAOException {

        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet rs=null;

        int userID=-1;

        try {

            connection=connectionPool.takeConnection();
            statement=connection.prepareStatement(GET_USER_BY_LOGIN);
            statement.setString(LOGIN_INDEX,user.getLogin());
            rs=statement.executeQuery();

            if (rs.next()){
                return userID;
            } else {

                statement=connection.prepareStatement(ADD_USER);
                statement.setString(LOGIN_INDEX,user.getLogin());
                statement.setString(PASSWORD_INDEX,user.getPassword());
                statement.setString(ROLE_INDEX,user.getRole().toString());
                statement.setString(NAME_INDEX, user.getName());
                statement.setString(SURNAME_INDEX,user.getSurname());
                statement.setString(EMAIL_INDEX,user.getEmail());
                statement.setDate(REG_DATE_INDEX,java.sql.Date.valueOf(user.getRegDate()));
                statement.setString(IMAGE_NAME_INDEX,user.getImageName());

                statement.execute();

                statement=connection.prepareStatement(GET_USER_ID);
                statement.setString(LOGIN_INDEX,user.getLogin());
                rs=statement.executeQuery();

                if(rs.next()){
                    userID=rs.getInt(ID_KEY);
                }
            }

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);

        } finally {
            connectionPool.closeConnection(connection,statement,rs);
        }

        return userID;
    }

    @Override
    public void editUser(User user) throws DAOException {

        Connection connection=null;
        PreparedStatement statement=null;

        try{

            connection=connectionPool.takeConnection();
            statement=connection.prepareStatement(UPDATE_USER);
            statement.setString(LOGIN_INDEX,user.getLogin());
            statement.setString(PASSWORD_INDEX,user.getPassword());
            statement.setString(ROLE_INDEX,user.getRole().toString());
            statement.setString(NAME_INDEX, user.getName());
            statement.setString(SURNAME_INDEX,user.getSurname());
            statement.setString(EMAIL_INDEX,user.getEmail());
            statement.setInt(ID_INDEX,user.getId());
            statement.setString(IMAGE_NAME_INDEX,user.getImageName());

            statement.execute();

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);

        } finally {
            connectionPool.closeConnection(connection,statement);
        }
    }

    @Override
    public boolean deleteUser(int userID) throws DAOException {

        Connection connection=null;
        PreparedStatement statement=null;
        boolean isDeleted=false;

        try{

            connection=connectionPool.takeConnection();
            statement=connection.prepareStatement(DELETE_USER);
            statement.setInt(FIRST_INDEX,userID);
            statement.execute();

            isDeleted=true;

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);

        } finally {
            connectionPool.closeConnection(connection,statement);
        }

        return isDeleted;
    }


    @Override
    public void setImageName(String name, User user) throws DAOException {

        Connection connection=null;
        PreparedStatement statement=null;

        try {

            connection=connectionPool.takeConnection();
            statement=connection.prepareStatement(SET_IMAGE);
            statement.setString(FIRST_INDEX, name);
            statement.setInt(SECOND_INDEX,user.getId());
            statement.execute();

        } catch (ConnectionPoolException | SQLException e) {
            throw  new DAOException(e);
        }
    }
}
