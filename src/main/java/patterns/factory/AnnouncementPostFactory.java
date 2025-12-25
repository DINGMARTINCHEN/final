// src/patterns/factory/AnnouncementPostFactory.java
package patterns.factory;

import model.Post;
import java.util.Date;

/**
 * 具体工厂 - 创建公告帖子
 */
public class AnnouncementPostFactory extends PostFactory {
    @Override
    public Post createPost(String title, String content, int userId, int boardId, String attachment) {
        Post post = new Post();
        post.setTitle("[公告] " + title);
        post.setContent("<div style='background-color:#f0f8ff; padding:10px; border-left:4px solid #007bff;'>" +
                content + "</div>");
        post.setUserId(userId);
        post.setBoardId(boardId);
        post.setCreatedAt(new Date());
        post.setAttachment(attachment);
        post.setPinned(true);  // 公告自动置顶
        return post;
    }
}