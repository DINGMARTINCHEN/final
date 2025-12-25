import controller.UserController;
import view.LoginView;

import javax.swing.*;

public class CampusForumApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserController userController = new UserController();
            new LoginView(userController);
        });
    }
}