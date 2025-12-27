package controller;

/**
 * 控制器工厂类 - 统一管理控制器对象
 */
public class ControllerFactory {
    private static UserController userController;
    private static PostController postController;
    private static ReplyController replyController;
    private static LikeController likeController;

    public static synchronized UserController getUserController() {
        if (userController == null) {
            userController = new UserController();
        }
        return userController;
    }

    public static synchronized PostController getPostController() {
        if (postController == null) {
            postController = new PostController();
        }
        return postController;
    }

    public static synchronized ReplyController getReplyController() {
        if (replyController == null) {
            replyController = new ReplyController();
        }
        return replyController;
    }

    public static synchronized LikeController getLikeController() {
        if (likeController == null) {
            likeController = new LikeController();
        }
        return likeController;
    }
}