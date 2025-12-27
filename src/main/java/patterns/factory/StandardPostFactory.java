package patterns.factory;

import model.Post;
import java.util.Date;

/**
 * 具体工厂 - 创建标准帖子（可带附件）
 */
public class StandardPostFactory extends PostFactory {
    @Override
    public Post createPost(String title, String content, int userId, int boardId, String attachment) {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setUserId(userId);
        post.setBoardId(boardId);
        post.setCreatedAt(new Date());     // 设置当前时间
        post.setAttachment(attachment);    // 可为空
        return post;
    }
}