// src/controller/LikeController.java
package controller;

import service.LikeService;
import model.Post;

import javax.swing.*;
import java.util.List;

/**
 * 点赞控制器
 */
public class LikeController {
    public LikeService likeService;

    public LikeController() {
        this.likeService = new LikeService();
    }

    /**
     * 点赞/取消点赞
     */
    public boolean toggleLike(int userId, int postId) {
        try {
            return likeService.likePost(userId, postId);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "操作失败：" + e.getMessage());
            return false;
        }
    }

    /**
     * 使用访问者模式点赞
     */
    public String likeWithVisitor(Post post, int userId) {
        try {
            return likeService.executeLikeVisitor(post, userId);
        } catch (Exception e) {
            return "操作失败：" + e.getMessage();
        }
    }

    /**
     * 获取帖子点赞数
     */
    public int getLikeCount(int postId) {
        try {
            return likeService.getLikeCount(postId);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "获取点赞数失败：" + e.getMessage());
            return 0;
        }
    }

    /**
     * 检查是否已点赞
     */
    public boolean hasLiked(int userId, int postId) {
        try {
            return likeService.hasLiked(userId, postId);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取用户点赞的帖子
     */
    public List<Integer> getLikedPostsByUser(int userId) {
        try {
            return likeService.getLikedPostsByUser(userId);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "获取点赞列表失败：" + e.getMessage());
            return null;
        }
    }

}