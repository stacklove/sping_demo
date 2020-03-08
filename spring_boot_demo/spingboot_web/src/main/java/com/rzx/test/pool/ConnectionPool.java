package com.rzx.test.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Vector;

/**
 * 数据库连接池<br>
 *
 * 1.初始化<br>
 * ####线程池核心容器 空闲线程数、活动线程数<br>
 * ###构造函数 1.1.1初始化线程,存放在空闲线程池中<br>
 * 2.获取连接 <br>
 * ####1.判断存在线程数是否大于最大线程 如果大于最大线程数,则进行等待...<br>
 * ####2.判断空闲线程数是否大于0 如果空闲线程数<0，创建新的连接<br>
 * ####3.如果空闲线程数>0，则获取当前空闲线程,存入在活动线程集合中 <br>
 * 3.释放连接 <br>
 * ####3.1.1.判断空闲线程数是否大于最大线程数 <br>
 * ####3.1.2.如果空闲线程数小于最大线程数,将该连接收回到 空闲 线程集合中<br>
 * ####3.1.3.删除该连接对应的活动线程集合数据<br>
 * <br>
 *
 *
 * 作者: 每特教育-余胜军<br>
 * 联系方式:QQ644064779|WWW.itmayiedu.com<br>
 */
public class ConnectionPool implements IConnectionPool {

    // 空闲线程集合
    private List<Connection> freeConnection = new Vector<Connection>();
    // 活动线程集合
    private List<Connection> activeConnection = new Vector<Connection>();
    // 记录线程总数
    private static int connCount = 0;
    private DbBean dbBean;

    public ConnectionPool(DbBean dbBean) {
        this.dbBean = dbBean;
        init();
    }

    public void init() {
        try {

            for (int i = 0; i < dbBean.getInitConnections(); i++) {
                Connection newConnection = newConnection();
                if (newConnection != null) {
                    // 添加到空闲线程中...
                    freeConnection.add(newConnection);
                }
            }

        } catch (Exception e) {

        }
    }

    // 创建新的Connection
    private Connection newConnection() {
        try {
            if (dbBean == null) {
                return null;
            }
            Class.forName(dbBean.getDriverName());
            Connection connection = DriverManager.getConnection(dbBean.getUrl(), dbBean.getUserName(),
                    dbBean.getPassword());
            connCount++;
            return connection;
        } catch (Exception e) {
            return null;
        }

    }

    public Connection getConnection() {
        // * ####1.判断活动线程数是否大于最大线程 如果大于最大线程数,则进行等待...<br>
        Connection connection = null;
        try {

            if (connCount < dbBean.getMaxActiveConnections()) {
                // 还有活动线程可以使用
                // * ####2.判断空闲线程数是否大于0 如果空闲线程数<0，创建新的连接<br>
                if (freeConnection.size() > 0) {
                    connection = freeConnection.remove(0);// 等于freeConnection.get(0);freeConnection.remove(0);
                } else {
                    // 创建新的连接
                    connection = newConnection();
                }

                boolean available = isAvailable(connection);
                if (available) {
                    activeConnection.add(connection);
                } else {
                    connCount--;// i--操作
                    connection = getConnection();// 递归调用getConnection方法
                }
            } else {
                // 大于最大线程数,进行等待,重新获取连接
                wait(dbBean.getConnTimeOut());
                connection = getConnection();// 递归调用getConnection方法
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // * ####3.如果空闲线程数>0，则获取当前空闲线程,存入在活动线程集合中 <br>
        return connection;
    }

    // 判断连接是否可用
    public boolean isAvailable(Connection connection) {
        try {
            if (connection == null || connection.isClosed()) {
                return false;
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        return true;

    }

    public void releaseConnection(Connection connection) {
        try {
            if (connection == null) {
                return;
            }
            if (isAvailable(connection)) {
                // 判断空闲线程数是否大于最大线程数
                if (freeConnection.size() < dbBean.getMaxConnections()) {
                    freeConnection.add(connection);
                } else {
                    // 空闲线程数已经满了
                    connection.close();
                }
                activeConnection.remove(connection);
                connCount--;
                notifyAll();

            }

        } catch (Exception e) {

        }

    }

}
