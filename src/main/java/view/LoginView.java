package view;

import controller.UserController;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private UserController controller;

    public LoginView(UserController controller) {
        this.controller = controller;

        setTitle("校园论坛系统 - 登录");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("用户名："));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("密码："));
        passwordField = new JPasswordField();
        add(passwordField);

        loginButton = new JButton("登录");
        add(loginButton);

        registerButton = new JButton("注册");
        add(registerButton);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "请填写完整信息！");
                return;
            }
            controller.loginUser(username, password, this);
        });

        registerButton.addActionListener(e -> {
            new RegisterView(controller);
        });

        setVisible(true);
    }
}