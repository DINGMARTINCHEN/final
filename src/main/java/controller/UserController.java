// src/controller/UserController.java
package controller;

import service.UserService;
import model.User;
import view.LoginView;
import view.MainForumView;

import javax.swing.*;

/**
 * 用户控制器 - 处理用户相关的业务逻辑
 */
public class UserController {
    private UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    /**
     * 用户注册
     */
    public void registerUser(String username, String password, String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole("user");  // 默认普通用户
        user.setEmail(email);

        try {
            boolean success = userService.registerUser(user);
            if (success) {
                JOptionPane.showMessageDialog(null, "注册成功！");
            } else {
                JOptionPane.showMessageDialog(null, "用户名已存在！");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "注册失败：" + e.getMessage());
        }
    }

    /**
     * 用户登录
     */
    public User loginUser(String username, String password, LoginView loginView) {
        try {
            User user = userService.loginUser(username, password);
            if (user != null) {
                JOptionPane.showMessageDialog(null, "登录成功！欢迎 " + username);

                // 登录成功：关闭登录窗口，打开主论坛界面
                loginView.dispose();
                MainForumView mainForumView = new MainForumView(user);
                mainForumView.setVisible(true);

                return user;
            } else {
                JOptionPane.showMessageDialog(null, "用户名或密码错误！");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "登录失败：" + e.getMessage());
        }
        return null;
    }

    /**
     * 更新用户信息
     */
    public void updateUser(User user) {
        try {
            boolean success = userService.updateUser(user);
            if (success) {
                JOptionPane.showMessageDialog(null, "更新成功！");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "更新失败：" + e.getMessage());
        }
    }
}