// src/service/LikeService.java
package service;

import controller.LikeController;
import dao.LikeDAO;
import dao.impl.LikeDAOImpl;
import patterns.visitor.LikeVisitor;
import model.Post;
import service.exception.ServiceException;
import view.MainForumView;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

/**
 * 点赞服务层
 */
public class LikeService {
    private LikeDAO likeDAO;

    public LikeService() {
        this.likeDAO = new LikeDAOImpl();
    }

    /**
     * 点赞帖子
     */
    public boolean likePost(int userId, int postId) {
        try {
            if (hasLiked(userId, postId)) {
                likeDAO.cancelLike(userId, postId);
            } else {
                likeDAO.addLike(userId, postId);
            }
            return true;
        } catch (SQLException e) {
            throw new ServiceException("点赞操作失败", e);
        }
    }

    /**
     * 检查是否已点赞
     */
    public boolean hasLiked(int userId, int postId) {
        try {
            return likeDAO.hasLiked(userId, postId);
        } catch (SQLException e) {
            throw new ServiceException("检查点赞状态失败", e);
        }
    }

    /**
     * 获取帖子点赞数
     */
    public int getLikeCount(int postId) {
        try {
            return likeDAO.getLikeCount(postId);
        } catch (SQLException e) {
            throw new ServiceException("获取点赞数失败", e);
        }
    }

    /**
     * 获取用户点赞的帖子列表
     */
    public List<Integer> getLikedPostsByUser(int userId) {
        try {
            return likeDAO.getLikedPostsByUser(userId);
        } catch (SQLException e) {
            throw new ServiceException("获取用户点赞列表失败", e);
        }
    }

    /**
     * 使用访问者模式执行点赞操作
     */
    public String executeLikeVisitor(Post post, int userId) {
        LikeVisitor visitor = new LikeVisitor(userId);
        return post.accept(visitor);
    }

    // 更新点赞按钮文本
    public void updateLikeButtonText(JButton likeButton, int postId, MainForumView mainForumView, LikeController likeController) {
        boolean hasLiked = likeController.hasLiked(mainForumView.currentUser.getId(), postId);
        int likeCount = likeController.getLikeCount(postId);

        if (hasLiked) {
            likeButton.setText("取消点赞 (" + likeCount + ")");
            likeButton.setForeground(Color.RED);
        } else {
            likeButton.setText("点赞 (" + likeCount + ")");
            likeButton.setForeground(Color.BLACK);
        }
    }
}