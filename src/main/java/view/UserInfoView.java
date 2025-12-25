package view;

import controller.UserController;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class UserInfoView extends JFrame {
    private User currentUser;
    private Runnable refreshCallback;
    private UserController userController;

    private JTextField usernameField;
    private JTextField emailField;
    private JButton saveButton;

    public UserInfoView(User user, Runnable refreshCallback) {
        this.currentUser = user;
        this.refreshCallback = refreshCallback;
        this.userController = new UserController();

        setTitle("个人信息维护");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("用户名："));
        usernameField = new JTextField(user.getUsername());
        usernameField.setEditable(false);
        add(usernameField);

        add(new JLabel("邮箱："));
        emailField = new JTextField(user.getEmail());
        add(emailField);

        add(new JLabel("角色："));
        JTextField roleField = new JTextField(getRoleName(user.getRole()));
        roleField.setEditable(false);
        add(roleField);

        saveButton = new JButton("保存修改");
        saveButton.addActionListener(e -> saveChanges());
        add(saveButton);

        JButton cancelButton = new JButton("取消");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);

        setVisible(true);
    }

    private void saveChanges() {
        String newEmail = emailField.getText().trim();
        if (newEmail.isEmpty()) {
            JOptionPane.showMessageDialog(this, "邮箱不能为空！");
            return;
        }

        currentUser.setEmail(newEmail);
        userController.updateUser(currentUser);

        dispose();
        if (refreshCallback != null) refreshCallback.run();
    }

    private String getRoleName(String role) {
        return switch (role) {
            case "admin" -> "管理员";
            case "moderator" -> "版主";
            default -> "普通用户";
        };
    }
}