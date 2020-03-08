package com.rzx.test.pool;

public class ConnectionPoolManager {

    private void ConnectionPoolManager(){

    }
    private static ConnectionPoolManager connectionPoolManager = new ConnectionPoolManager();
    static {
        DbBean dbBean  = new DbBean();
        ConnectionPool connectionPool = new ConnectionPool(dbBean);
    }

    public static ConnectionPoolManager getInstance(){
        return connectionPoolManager;
    }



}
