package by.epam.like_it.dao.connection_pool;

public class ConnectionPoolException extends Exception {
    private static final long serialVersionUID = 1L;
    public ConnectionPoolException(String message, Exception e){
        super(message, e);
    }
}
