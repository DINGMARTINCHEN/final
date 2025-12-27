package service;

/**
 * 服务工厂类 - 统一管理服务对象
 */
public class ServiceFactory {
    private static UserService userService;
    private static PostService postService;
    private static ReplyService replyService;
    private static LikeService likeService;

    public static synchronized UserService getUserService() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public static synchronized PostService getPostService() {
        if (postService == null) {
            postService = new PostService();
        }
        return postService;
    }

    public static synchronized ReplyService getReplyService() {
        if (replyService == null) {
            replyService = new ReplyService();
        }
        return replyService;
    }
    public static synchronized LikeService getLikeService() {
        if (likeService == null) {
            likeService = new LikeService();
        }
        return likeService;
    }
}