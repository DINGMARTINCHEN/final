package dao;

import model.User;
import java.sql.SQLException;
import java.util.List;

/**
 * 用户数据访问对象接口
 */
public interface UserDAO {
    void addUser(User user) throws SQLException;                    // 注册新增用户
    User getUserByUsername(String username) throws SQLException;    // 根据用户名查询用户（用于登录）
    void updateUser(User user) throws SQLException;                 // 更新用户信息
    List<User> getAllUsers() throws SQLException;                   // 获取所有用户（管理员功能）
}