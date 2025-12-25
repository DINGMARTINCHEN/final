// src/patterns/visitor/VisitorTest.java
package patterns.visitor;

import model.Post;
import model.User;
import java.util.Date;

/**
 * 访问者模式验证程序
 * 运行后显示个人信息，并演示点赞功能
 */
public class VisitorTest {
    public static void main(String[] args) {
        // 显著位置显示个人信息
        System.out.println("================================================");
        System.out.println("【学生信息】学号后两位-姓名：50-丁士程");
        System.out.println("【设计模式】访问者模式（Visitor）验证");
        System.out.println("【功能说明】为帖子动态添加点赞功能");
        System.out.println("================================================");

        // 创建测试帖子和用户
        Post post = new Post();
        post.setId(1);
        post.setTitle("Java设计模式学习心得");
        post.setContent("今天学习了访问者模式，感觉非常有用！");
        post.setCreatedAt(new Date());
        post.setViews(100);

        User user = new User();
        user.setId(1);
        user.setUsername("张三");

        System.out.println("\n=== 测试帖子信息 ===");
        System.out.println("标题：" + post.getTitle());
        System.out.println("内容：" + post.getContent());
        System.out.println("浏览量：" + post.getViews());
        System.out.println("发布时间：" + post.getCreatedAt());

        System.out.println("\n=== 使用访问者模式执行点赞操作 ===");

        // 第一次点赞
        PostVisitor likeVisitor1 = new LikeVisitor(user.getId());
        String result1 = post.accept(likeVisitor1);
        System.out.println("第一次操作结果：" + result1);

        // 第二次操作（取消点赞）
        PostVisitor likeVisitor2 = new LikeVisitor(user.getId());
        String result2 = post.accept(likeVisitor2);
        System.out.println("第二次操作结果：" + result2);

        // 第三次操作（再次点赞）
        PostVisitor likeVisitor3 = new LikeVisitor(user.getId());
        String result3 = post.accept(likeVisitor3);
        System.out.println("第三次操作结果：" + result3);

        System.out.println("\n=== 访问者模式优势演示 ===");
        System.out.println("1. 开闭原则：不修改Post类，添加新功能");
        System.out.println("2. 单一职责：点赞逻辑封装在LikeVisitor中");
        System.out.println("3. 可扩展性：轻松添加收藏、分享等新访问者");
        System.out.println("4. 集中管理：相关操作集中在一个访问者中");

        System.out.println("\n=== 可扩展性演示 ===");
        System.out.println("可以轻松创建新的访问者：");
        System.out.println("- FavoriteVisitor: 收藏功能");
        System.out.println("- ShareVisitor: 分享功能");
        System.out.println("- ReportVisitor: 举报功能");
        System.out.println("- BookmarkVisitor: 书签功能");

        System.out.println("\n=== 与传统方法对比 ===");
        System.out.println("传统方法：需要在Post类中添加like()、favorite()等方法");
        System.out.println("访问者模式：每个功能独立成访问者，不污染Post类");
    }
}