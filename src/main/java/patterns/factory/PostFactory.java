package patterns.factory;

import model.Post;

/**
 * 抽象工厂类 - 定义创建帖子的方法
 */
public abstract class PostFactory {
    /**
     * 创建帖子对象的抽象方法，由子类实现
     */
    public abstract Post createPost(String title, String content, int userId, int boardId, String attachment);
}