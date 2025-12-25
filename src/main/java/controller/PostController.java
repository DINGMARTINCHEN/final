// src/controller/PostController.java
package controller;

import patterns.strategy.SortByDate;
import patterns.strategy.SortByViews;
import service.PostService;
import model.Post;
import model.User;
import patterns.factory.PostFactory;
import patterns.factory.PostFactorySelector;
import view.MainForumView;

import javax.swing.*;
import java.util.List;

/**
 * 帖子控制器 - 处理帖子相关的业务逻辑
 */
public class PostController {
    private PostService postService;

    public PostController() {
        this.postService = new PostService();
    }

    /**
     * 创建新帖子
     */
    public boolean createPost(String title, String content, int userId, int boardId,
                              String attachment, String postType, User user) {
        try {
            // 使用工厂模式创建帖子
            String factoryType = convertPostType(postType);
            PostFactory factory = PostFactorySelector.getFactory(user, factoryType);
            Post post = factory.createPost(title, content, userId, boardId, attachment);

            return postService.createPost(post, postType, userId);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "发帖失败：" + e.getMessage());
            return false;
        }
    }

    /**
     * 获取版块帖子
     */
    public List<Post> getBoardPosts(int boardId, int page, int pageSize) {
        try {
            return postService.getPostsByBoard(boardId, page, pageSize);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "加载帖子失败：" + e.getMessage());
            return null;
        }
    }

    /**
     * 搜索帖子
     */
    public List<Post> searchPosts(String keyword) {
        try {
            return postService.searchPosts(keyword);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "搜索失败：" + e.getMessage());
            return null;
        }
    }

    /**
     * 获取帖子详情
     */
    public Post getPostDetail(int postId) {
        try {
            return postService.getPostById(postId);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "获取帖子详情失败：" + e.getMessage());
            return null;
        }
    }

    /**
     * 更新帖子
     */
    public boolean updatePost(Post post) {
        try {
            return postService.updatePost(post);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "更新帖子失败：" + e.getMessage());
            return false;
        }
    }

    /**
     * 删除帖子
     */
    public boolean deletePost(int postId) {
        try {
            return postService.deletePost(postId);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "删除帖子失败：" + e.getMessage());
            return false;
        }
    }

    /**
     * 置顶/取消置顶
     */
    public boolean togglePinPost(int postId, boolean pin) {
        try {
            return postService.togglePinPost(postId, pin);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "操作失败：" + e.getMessage());
            return false;
        }
    }

    /**
     * 转换帖子类型
     */
    private String convertPostType(String postType) {
        switch (postType) {
            case "置顶帖子":
                return "pinned";
            case "公告帖子":
                return "announcement";
            default:
                return "standard";
        }
    }

    /**
     * 排序并显示帖子
     *
     * @param mainForumView
     */
    public void sortAndDisplayPosts(MainForumView mainForumView) {
        if (mainForumView.currentPosts.isEmpty()) return;

        if ("date".equals(mainForumView.currentSort)) {
            mainForumView.postSorter.setStrategy(new SortByDate());
            mainForumView.postSorter.sortPosts(mainForumView.currentPosts);
        } else if ("views".equals(mainForumView.currentSort)) {
            mainForumView.postSorter.setStrategy(new SortByViews());
            mainForumView.postSorter.sortPosts(mainForumView.currentPosts);
        } else if ("likes".equals(mainForumView.currentSort)) {
            mainForumView.currentPosts.sort((p1, p2) -> Integer.compare(p2.getLikeCount(), p1.getLikeCount()));
        }

        mainForumView.refreshPostTable();
    }

}