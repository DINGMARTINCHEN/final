package patterns.visitor;

import model.Post;

/**
 * 帖子访问者接口
 * 使用访问者模式为帖子添加点赞功能
 */
public interface PostVisitor {
    /**
     * 访问帖子
     * @param post 被访问的帖子
     * @return 操作结果信息
     */
    String visit(Post post);

    /**
     * 获取操作名称
     */
    String getOperationName();

    /**
     * 获取操作描述
     */
    String getOperationDescription();
}