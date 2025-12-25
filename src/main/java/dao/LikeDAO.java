// src/dao/LikeDAO.java
package dao;

import java.sql.SQLException;

/**
 * 点赞数据访问接口
 */
public interface LikeDAO {
    /**
     * 添加点赞
     */
    void addLike(int userId, int postId) throws SQLException;

    /**
     * 取消点赞
     */
    void cancelLike(int userId, int postId) throws SQLException;

    /**
     * 检查是否已点赞
     */
    boolean hasLiked(int userId, int postId) throws SQLException;

    /**
     * 获取帖子点赞数
     */
    int getLikeCount(int postId) throws SQLException;

    /**
     * 获取用户点赞的帖子列表
     */
    java.util.List<Integer> getLikedPostsByUser(int userId) throws SQLException;
}