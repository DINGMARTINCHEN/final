package view;

import controller.UserController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField emailField;
    private JButton registerButton;
    private JButton cancelButton;
    private UserController controller;

    public RegisterView(UserController controller) {
        this.controller = controller;

        setTitle("校园论坛系统 - 注册新用户");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 10, 10));

        add(new JLabel("用户名："));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("密码："));
        passwordField = new JPasswordField();
        add(passwordField);

        add(new JLabel("确认密码："));
        confirmPasswordField = new JPasswordField();
        add(confirmPasswordField);

        add(new JLabel("邮箱："));
        emailField = new JTextField();
        add(emailField);

        registerButton = new JButton("立即注册");
        add(registerButton);

        cancelButton = new JButton("取消");
        add(cancelButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());
                String email = emailField.getText().trim();

                if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(RegisterView.this, "请填写完整信息！");
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(RegisterView.this, "两次密码不一致！");
                    return;
                }

                controller.registerUser(username, password, email);
                JOptionPane.showMessageDialog(RegisterView.this, "注册成功！请返回登录");
                dispose();
            }
        });

        cancelButton.addActionListener(e -> dispose());
        setVisible(true);
    }
}