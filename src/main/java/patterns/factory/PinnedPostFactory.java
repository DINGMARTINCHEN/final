// src/patterns/factory/PinnedPostFactory.java
package patterns.factory;

import model.Post;
import java.util.Date;

/**
 * 具体工厂 - 创建置顶帖子
 */
public class PinnedPostFactory extends PostFactory {
    @Override
    public Post createPost(String title, String content, int userId, int boardId, String attachment) {
        Post post = new Post();
        post.setTitle("[置顶] " + title);
        post.setContent(content);
        post.setUserId(userId);
        post.setBoardId(boardId);
        post.setCreatedAt(new Date());
        post.setAttachment(attachment);
        post.setPinned(true);  // 设置为置顶
        return post;
    }
}