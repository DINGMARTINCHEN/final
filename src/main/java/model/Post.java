// src/model/Post.java
package model;

import patterns.visitor.PostElement;
import patterns.visitor.PostVisitor;

import java.util.Date;

/**
 * 帖子实体类
 * 对应数据库中的 posts 表
 * 实现PostElement接口以支持访问者模式
 */
public class Post implements PostElement {
    private int id;
    private String title;
    private String content;
    private int userId;
    private int boardId;
    private Date createdAt;
    private String attachment;
    private int views = 0;
    private int replyCount = 0;
    private String username;
    private boolean pinned = false;
    private int likeCount = 0; // 新增点赞数字段

    // 实现访问者模式
    @Override
    public String accept(PostVisitor visitor) {
        return visitor.visit(this);
    }

    // Getter 和 Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getBoardId() { return boardId; }
    public void setBoardId(int boardId) { this.boardId = boardId; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public String getAttachment() { return attachment; }
    public void setAttachment(String attachment) { this.attachment = attachment; }

    public int getViews() { return views; }
    public void setViews(int views) { this.views = views; }

    public int getReplyCount() { return replyCount; }
    public void setReplyCount(int replyCount) { this.replyCount = replyCount; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public boolean isPinned() { return pinned; }
    public void setPinned(boolean pinned) { this.pinned = pinned; }

    public int getLikeCount() { return likeCount; }
    public void setLikeCount(int likeCount) { this.likeCount = likeCount; }
}