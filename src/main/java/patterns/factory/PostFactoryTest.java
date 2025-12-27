package patterns.factory;

import model.Post;
import model.User;

/**
 * 工厂方法模式验证程序
 * 运行后在控制台显著位置显示个人信息，并创建帖子对象
 */
public class PostFactoryTest {
    public static void main(String[] args) {
        // 显著位置显示个人信息
        System.out.println("================================================");
        System.out.println("【学生信息】学号后两位-姓名：50-丁士程");
        System.out.println("【设计模式】工厂方法模式（Factory Method）验证");
        System.out.println("【功能说明】通过不同工厂创建不同类型的帖子");
        System.out.println("================================================");

        // 创建测试用户
        User adminUser = new User();
        adminUser.setId(1);
        adminUser.setUsername("管理员");
        adminUser.setRole("admin");

        User normalUser = new User();
        normalUser.setId(2);
        normalUser.setUsername("普通用户");
        normalUser.setRole("user");

        // 演示标准帖子工厂
        System.out.println("\n=== 标准帖子工厂 ===");
        PostFactory standardFactory = new StandardPostFactory();
        Post standardPost = standardFactory.createPost(
                "学习经验分享",
                "今天想和大家分享一些高效的学习方法...",
                1, 1, "学习计划表.xlsx"
        );
        displayPostInfo(standardPost);

        // 演示公告帖子工厂
        System.out.println("\n=== 公告帖子工厂 ===");
        PostFactory announcementFactory = new AnnouncementPostFactory();
        Post announcementPost = announcementFactory.createPost(
                "重要通知：校园网络维护",
                "本周六晚上10点至周日早上6点进行网络维护...",
                2, 1, "网络维护公告.pdf"
        );
        displayPostInfo(announcementPost);

        // 演示置顶帖子工厂
        System.out.println("\n=== 置顶帖子工厂 ===");
        PostFactory pinnedFactory = new PinnedPostFactory();
        Post pinnedPost = pinnedFactory.createPost(
                "【置顶】新生入学指南",
                "欢迎新同学！请仔细阅读本指南...",
                3, 1, "新生手册.pdf"
        );
        displayPostInfo(pinnedPost);

        // 演示工厂选择器
        System.out.println("\n=== 工厂选择器演示 ===");
        PostFactory selectedFactory = PostFactorySelector.getFactory(adminUser, "announcement");
        Post selectedPost = selectedFactory.createPost(
                "选中的公告帖子",
                "这是通过工厂选择器创建的帖子",
                4, 1, null
        );
        displayPostInfo(selectedPost);

        // 演示普通用户只能创建标准帖子
        System.out.println("\n=== 普通用户工厂选择演示 ===");
        PostFactory normalUserFactory = PostFactorySelector.getFactory(normalUser, "announcement");
        Post normalUserPost = normalUserFactory.createPost(
                "普通用户的帖子",
                "普通用户尝试创建公告帖子，但会被降级为标准帖子",
                5, 1, null
        );
        displayPostInfo(normalUserPost);
    }

    private static void displayPostInfo(Post post) {
        System.out.println("标题：" + post.getTitle());
        System.out.println("内容：" + post.getContent().substring(0, Math.min(50, post.getContent().length())) + "...");
        System.out.println("用户ID：" + post.getUserId());
        System.out.println("版块ID：" + post.getBoardId());
        System.out.println("附件：" + (post.getAttachment() != null ? post.getAttachment() : "无"));
        System.out.println("创建时间：" + post.getCreatedAt());
        System.out.println("是否置顶：" + post.isPinned());
        System.out.println("---");
    }
}