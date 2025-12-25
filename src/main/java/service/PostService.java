// src/service/PostService.java
package service;

import dao.PostDAO;
import dao.impl.PostDAOImpl;
import model.Post;
import patterns.factory.PostFactory;
import patterns.factory.PostFactorySelector;
import patterns.observer.PostSubject;
import patterns.observer.UserObserver;
import service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

/**
 * 帖子业务逻辑服务层
 */
public class PostService {
    private PostDAO postDAO;
    private PostSubject postSubject;

    public PostService() {
        this.postDAO = new PostDAOImpl();
        this.postSubject = new PostSubject();
    }

    /**
     * 创建帖子
     */
    public boolean createPost(Post post, String postType, int userId) {
        try {
            // 这里需要获取用户对象，简化实现
            postDAO.addPost(post);
            // 发布通知
            postSubject.postUpdated(post.getTitle());
            return true;
        } catch (SQLException e) {
            throw new service.exception.ServiceException("创建帖子失败", e);
        }
    }

    /**
     * 获取版块帖子（分页）
     */
    public List<Post> getPostsByBoard(int boardId, int page, int pageSize) {
        try {
            return postDAO.getPostsByBoardIdPaged(boardId, page, pageSize);
        } catch (SQLException e) {
            throw new ServiceException("获取帖子列表失败", e);
        }
    }

    /**
     * 获取帖子详情
     */
    public Post getPostById(int postId) {
        try {
            Post post = postDAO.getPostById(postId);
            if (post != null) {
                // 增加浏览量
                postDAO.incrementViews(postId);
            }
            return post;
        } catch (SQLException e) {
            throw new ServiceException("获取帖子详情失败", e);
        }
    }

    /**
     * 搜索帖子
     */
    public List<Post> searchPosts(String keyword) {
        try {
            return postDAO.searchPosts(keyword);
        } catch (SQLException e) {
            throw new ServiceException("搜索帖子失败", e);
        }
    }

    /**
     * 更新帖子
     */
    public boolean updatePost(Post post) {
        try {
            postDAO.updatePost(post);
            postSubject.postUpdated(post.getTitle());
            return true;
        } catch (SQLException e) {
            throw new ServiceException("更新帖子失败", e);
        }
    }

    /**
     * 删除帖子
     */
    public boolean deletePost(int postId) {
        try {
            postDAO.deletePost(postId);
            return true;
        } catch (SQLException e) {
            throw new ServiceException("删除帖子失败", e);
        }
    }

    /**
     * 置顶/取消置顶
     */
    public boolean togglePinPost(int postId, boolean pin) {
        try {
            if (pin) {
                postDAO.pinPost(postId);
            } else {
                postDAO.unpinPost(postId);
            }
            return true;
        } catch (SQLException e) {
            throw new ServiceException("置顶操作失败", e);
        }
    }
}