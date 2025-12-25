// src/service/UserService.java
package service;

import dao.UserDAO;
import dao.impl.UserDAOImpl;
import model.User;
import service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

/**
 * 用户业务逻辑服务层
 */
public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAOImpl();
    }

    /**
     * 用户注册
     */
    public boolean registerUser(User user) {
        try {
            // 检查用户名是否已存在
            if (userDAO.getUserByUsername(user.getUsername()) != null) {
                return false;
            }
            userDAO.addUser(user);
            return true;
        } catch (SQLException e) {
            throw new ServiceException("注册失败", e);
        }
    }

    /**
     * 用户登录
     */
    public User loginUser(String username, String password) {
        try {
            User user = userDAO.getUserByUsername(username);
            if (user != null && user.getPassword().equals(password)) {
                return user;
            }
            return null;
        } catch (SQLException e) {
            throw new ServiceException("登录失败", e);
        }
    }

    /**
     * 更新用户信息
     */
    public boolean updateUser(User user) {
        try {
            userDAO.updateUser(user);
            return true;
        } catch (SQLException e) {
            throw new ServiceException("更新用户信息失败", e);
        }
    }

    /**
     * 获取所有用户（管理员功能）
     */
    public List<User> getAllUsers() {
        try {
            return userDAO.getAllUsers();
        } catch (SQLException e) {
            throw new ServiceException("获取用户列表失败", e);
        }
    }
}