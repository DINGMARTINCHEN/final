package patterns.decorator;

import model.Post;

/**
 * 装饰器模式验证程序
 * 运行后显示个人信息，并演示帖子内容增强
 */
public class DecoratorTest {
    public static void main(String[] args) {
        // 显著位置显示个人信息
        System.out.println("================================================");
        System.out.println("【学生信息】学号后两位-姓名：50-丁士程");
        System.out.println("【设计模式】装饰器模式（Decorator）验证");
        System.out.println("【功能说明】动态为帖子对象添加附件功能");
        System.out.println("================================================");

        // 创建基本帖子
        Post basicPost = new Post();
        basicPost.setTitle("校园活动通知");
        basicPost.setContent("本周五下午2点将在操场举行篮球比赛，欢迎同学们踊跃参加！");
        basicPost.setUserId(1);
        basicPost.setBoardId(1);

        System.out.println("\n=== 原始帖子内容 ===");
        System.out.println("标题：" + basicPost.getTitle());
        System.out.println("内容：" + basicPost.getContent());
        System.out.println("附件：" + (basicPost.getAttachment() != null ? basicPost.getAttachment() : "无"));

        // 使用装饰器添加附件
        Post decoratedPost = new AttachmentDecorator(basicPost, "篮球比赛规则.pdf");

        System.out.println("\n=== 装饰后帖子内容 ===");
        System.out.println("标题：" + decoratedPost.getTitle());
        System.out.println("内容预览：");
        System.out.println(decoratedPost.getContent().substring(0, Math.min(100, decoratedPost.getContent().length())) + "...");
        System.out.println("附件：" + decoratedPost.getAttachment());

        // 演示装饰器的灵活性
        System.out.println("\n=== 装饰器模式优势演示 ===");
        System.out.println("1. 不修改原Post类，动态添加功能");
        System.out.println("2. 可以多层装饰，灵活组合功能");
        System.out.println("3. 保持接口一致性，客户端无需修改");
    }
}