package patterns.factory;

import model.User;

/**
 * 工厂选择器 - 根据用户角色和需求选择合适的工厂
 */
public class PostFactorySelector {

    /**
     * 根据用户角色和帖子类型选择合适的工厂
     */
    public static PostFactory getFactory(User user, String postType) {
        if ("announcement".equals(postType) && isAdminOrModerator(user)) {
            return new AnnouncementPostFactory();
        } else if ("pinned".equals(postType) && isAdminOrModerator(user)) {
            return new PinnedPostFactory();
        } else {
            return new StandardPostFactory();
        }
    }

    /**
     * 检查用户是否有管理员或版主权限
     */
    private static boolean isAdminOrModerator(User user) {
        String role = user.getRole();
        return "admin".equals(role) || "moderator".equals(role);
    }
}