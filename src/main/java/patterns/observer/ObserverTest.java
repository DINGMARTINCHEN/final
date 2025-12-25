// src/patterns/observer/ObserverTest.java
package patterns.observer;

/**
 * 观察者模式验证程序
 * 运行后显示个人信息，并模拟帖子更新通知多个观察者
 */
public class ObserverTest {
    public static void main(String[] args) {
        // 显著位置显示个人信息
        System.out.println("================================================");
        System.out.println("【学生信息】学号后两位-姓名：50-丁士程");
        System.out.println("【设计模式】观察者模式（Observer）验证");
        System.out.println("【功能说明】实现帖子更新时自动通知订阅用户");
        System.out.println("================================================");

        // 创建主题（被观察者）
        PostSubject subject = new PostSubject();

        System.out.println("\n=== 创建观察者（订阅用户） ===");
        // 创建多个观察者
        Observer user1 = new UserObserver("张三");
        Observer user2 = new UserObserver("李四");
        Observer user3 = new UserObserver("王五");
        Observer user4 = new UserObserver("赵六");

        System.out.println("已创建观察者：张三、李四、王五、赵六");

        // 注册观察者
        System.out.println("\n=== 注册观察者 ===");
        subject.addObserver(user1);
        subject.addObserver(user2);
        subject.addObserver(user3);
        System.out.println("已注册观察者：张三、李四、王五");

        // 第一次帖子更新
        System.out.println("\n=== 第一次帖子更新通知 ===");
        subject.postUpdated("校园美食节活动安排 - 时间地点确定");

        // 动态添加新的观察者
        System.out.println("\n=== 动态添加新观察者 ===");
        subject.addObserver(user4);
        System.out.println("新观察者赵六已注册");

        // 第二次帖子更新（所有观察者都会收到通知）
        System.out.println("\n=== 第二次帖子更新通知 ===");
        subject.postUpdated("校园美食节活动安排 - 新增特色小吃摊位");

        // 移除观察者
        System.out.println("\n=== 移除观察者 ===");
        subject.removeObserver(user2);
        System.out.println("观察者李四已取消订阅");

        // 第三次帖子更新（只有剩余观察者收到通知）
        System.out.println("\n=== 第三次帖子更新通知 ===");
        subject.postUpdated("校园美食节活动安排 - 天气预警通知");

        System.out.println("\n=== 观察者模式优势总结 ===");
        System.out.println("1. 松耦合：主题和观察者相互独立");
        System.out.println("2. 动态性：可随时添加/移除观察者");
        System.out.println("3. 广播机制：一次更新，多方通知");
    }
}