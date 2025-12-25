// src/patterns/visitor/FavoriteVisitor.java
package patterns.visitor;

import model.Post;

/**
 * 收藏访问者（示例）
 * 展示访问者模式的可扩展性
 */
public class FavoriteVisitor implements PostVisitor {
    private int userId;

    public FavoriteVisitor(int userId) {
        this.userId = userId;
    }

    @Override
    public String visit(Post post) {
        // 这里可以连接数据库实现收藏功能
        return String.format("用户%d收藏了帖子《%s》", userId, post.getTitle());
    }

    @Override
    public String getOperationName() {
        return "收藏功能";
    }

    @Override
    public String getOperationDescription() {
        return "为帖子添加收藏功能";
    }
}