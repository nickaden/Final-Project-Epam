package by.epam.like_it.dao.connection_pool;

import java.util.ResourceBundle;

public class DBResourceManager {

    private final static DBResourceManager instance = new DBResourceManager();
    private final static String PROPERTIES_PATH="db";


    private ResourceBundle bundle =
            ResourceBundle.getBundle(PROPERTIES_PATH);

    public static DBResourceManager getInstance() {
        return instance;
    } public String getValue(String key){
        return bundle.getString(key);
    }
}