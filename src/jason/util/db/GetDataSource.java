package jason.util.db;

import org.apache.log4j.Logger;

import java.sql.*;

/**
 * Created by Jason on 2016/9/15.
 */
public class GetDataSource {
    private static Logger logger = Logger.getLogger(GetDataSource.class);

    /**
     * 获得mysql数据库连接
     * @return mysql数据库连接实例
     */
    public Connection getMysqlConnect(){
        Connection connection = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        }catch (ClassNotFoundException e){
            logger.error("未找到mysql驱动...");
        }

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/university","root","ahgxtx");
        }catch (Exception e){
            logger.error("无法连接mysql数据库...");
        }
        return connection;
    }

    public void closeMysqlConnect(Connection connection, Statement statement, ResultSet rs){
        try {
            if (rs != null){
                rs.close();
            }
            if (statement != null){
                statement.close();
            }
            if (rs != null){
                rs.close();
            }
        }catch (SQLException e){
            logger.error("关闭数据库连接出错...");
        }
    }
}
