// Database Connection Utility
// src/db/DatabaseConnection.java
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库连接工具类
 * 提供获取MySQL连接的静态方法
 */
public class DatabaseConnection {
    // 数据库连接信息（请根据自己的MySQL配置修改）
    private static final String URL = "jdbc:mysql://localhost:3306/campus_forum?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";       // 修改为你的数据库用户名
    private static final String PASS = "1379";   // 修改为你的数据库密码

    /**
     * 获取数据库连接
     * @return Connection 对象
     * @throws SQLException 连接失败时抛出
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}