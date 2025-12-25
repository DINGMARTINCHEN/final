// src/patterns/strategy/StrategyTest.java
package patterns.strategy;

import model.Post;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 策略模式验证程序
 * 运行后显示个人信息，并演示帖子列表排序
 */
public class StrategyTest {
    public static void main(String[] args) {
        // 显著位置显示个人信息
        System.out.println("================================================");
        System.out.println("【学生信息】学号后两位-姓名：50-丁士程");

                System.out.println("【设计模式】策略模式（Strategy）验证");
        System.out.println("【功能说明】动态切换帖子排序策略");
        System.out.println("================================================");

        // 创建测试帖子列表
        List<Post> posts = createTestPosts();

        System.out.println("\n=== 原始帖子列表 ===");
        displayPosts(posts);

        // 使用时间排序策略
        System.out.println("\n=== 按时间排序（最新优先） ===");
        PostSorter sorter = new PostSorter();
        sorter.setStrategy(new SortByDate());
        List<Post> dateSorted = new ArrayList<>(posts);
        sorter.sortPosts(dateSorted);
        displayPosts(dateSorted);

        // 使用浏览量排序策略
        System.out.println("\n=== 按浏览量排序（热门优先） ===");
        sorter.setStrategy(new SortByViews());
        List<Post> viewsSorted = new ArrayList<>(posts);
        sorter.sortPosts(viewsSorted);
        displayPosts(viewsSorted);

        // 演示策略切换的灵活性
        System.out.println("\n=== 策略模式优势演示 ===");
        System.out.println("1. 算法封装：每种排序策略独立封装");
        System.out.println("2. 可扩展性：轻松添加新的排序策略");
        System.out.println("3. 运行时切换：无需修改客户端代码");
        System.out.println("4. 消除条件判断：避免复杂的if-else逻辑");
    }

    private static List<Post> createTestPosts() {
        List<Post> posts = new ArrayList<>();

        // 帖子1：旧帖子，高浏览量
        Post p1 = new Post();
        p1.setTitle("校园历史回顾");
        p1.setContent("回顾我校50年发展历程...");
        p1.setViews(1500);
        p1.setCreatedAt(new Date(1000000000000L)); // 2001年
        p1.setUsername("历史系主任");
        posts.add(p1);

        // 帖子2：新帖子，中等浏览量
        Post p2 = new Post();
        p2.setTitle("本周社团活动通知");
        p2.setContent("各社团本周活动安排如下...");
        p2.setViews(350);
        p2.setCreatedAt(new Date(System.currentTimeMillis() - 86400000)); // 昨天
        p2.setUsername("社团联合会");
        posts.add(p2);

        // 帖子3：最新帖子，低浏览量
        Post p3 = new Post();
        p3.setTitle("紧急通知：图书馆临时闭馆");
        p3.setContent("因设备检修，图书馆今天下午闭馆...");
        p3.setViews(80);
        p3.setCreatedAt(new Date()); // 现在
        p3.setUsername("图书馆管理员");
        posts.add(p3);

        // 帖子4：中等帖子，高浏览量
        Post p4 = new Post();
        p4.setTitle("期末考试安排");
        p4.setContent("本学期期末考试时间安排已公布...");
        p4.setViews(1200);
        p4.setCreatedAt(new Date(System.currentTimeMillis() - 604800000)); // 一周前
        p4.setUsername("教务处");
        posts.add(p4);

        return posts;
    }

    private static void displayPosts(List<Post> posts) {
        for (int i = 0; i < posts.size(); i++) {
            Post post = posts.get(i);
            System.out.printf("%d. %s (浏览:%d) - %s - %s%n",
                    i + 1,
                    post.getTitle(),
                    post.getViews(),
                    post.getUsername(),
                    post.getCreatedAt()
            );
        }
    }
}