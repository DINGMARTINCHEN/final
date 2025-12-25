// src/dao/PostDAO.java
package dao;

import model.Post;
import java.sql.SQLException;
import java.util.List;

public interface PostDAO {
    void addPost(Post post) throws SQLException;

    void updatePost(Post post) throws SQLException;

    void deletePost(int id) throws SQLException;

    List<Post> getPostsByBoardId(int boardId) throws SQLException;

    List<Post> getPostsByBoardIdPaged(int boardId, int page, int pageSize) throws SQLException;

    Post getPostById(int id) throws SQLException;
    List<Post> searchPosts(String keyword) throws SQLException;

    int countPostsByBoardId(int boardId) throws SQLException;

    void incrementViews(int id) throws SQLException;

    // 添加置顶相关方法
    void pinPost(int id) throws SQLException;

    void unpinPost(int id) throws SQLException;

    boolean isPostPinned(int id) throws SQLException;
}