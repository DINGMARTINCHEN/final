package model;

import java.util.Date;

/**
 * 回复实体类
 * 对应数据库中的 replies 表
 */
public class Reply {
    private int id;
    private String content;     // 回复内容
    private int postId;         // 所属帖子ID
    private int userId;         // 回复人ID
    private Date createdAt;     // 创建时间
    private String username;    // 回复人用户名

    // Getter 和 Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public int getPostId() { return postId; }
    public void setPostId(int postId) { this.postId = postId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}