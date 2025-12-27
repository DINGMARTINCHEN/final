package dao.impl;

import dao.PostDAO;
import db.DatabaseConnection;
import model.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * PostDAO 接口的实现类
 * 负责帖子相关的数据库操作
 */
public class PostDAOImpl implements PostDAO {

    @Override
    public void addPost(Post post) throws SQLException {
        String sql = "INSERT INTO posts (title, content, user_id, board_id, attachment) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, post.getTitle());
            pstmt.setString(2, post.getContent());
            pstmt.setInt(3, post.getUserId());
            pstmt.setInt(4, post.getBoardId());
            pstmt.setString(5, post.getAttachment());
            pstmt.executeUpdate();

            // 获取自增ID
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                post.setId(rs.getInt(1));
            }
        }
    }

    @Override
    public void updatePost(Post post) throws SQLException {
        String sql = "UPDATE posts SET title = ?, content = ?, attachment = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, post.getTitle());
            pstmt.setString(2, post.getContent());
            pstmt.setString(3, post.getAttachment());
            pstmt.setInt(4, post.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deletePost(int id) throws SQLException {
        String sql = "DELETE FROM posts WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    @Override
    public List<Post> getPostsByBoardId(int boardId) throws SQLException {
        return getPostsByBoardIdPaged(boardId, 1, Integer.MAX_VALUE);  // 默认全查
    }

    @Override
    public List<Post> getPostsByBoardIdPaged(int boardId, int page, int pageSize) throws SQLException {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT p.*, u.username, COUNT(r.id) as reply_count FROM posts p " +
                "JOIN users u ON p.user_id = u.id " +
                "LEFT JOIN replies r ON p.id = r.post_id " +
                "WHERE board_id = ? GROUP BY p.id ORDER BY p.pinned DESC, created_at DESC LIMIT ? OFFSET ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, boardId);
            pstmt.setInt(2, pageSize);
            pstmt.setInt(3, (page - 1) * pageSize);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getInt("id"));
                post.setTitle(rs.getString("title"));
                post.setContent(rs.getString("content"));
                post.setUserId(rs.getInt("user_id"));
                post.setBoardId(rs.getInt("board_id"));
                post.setCreatedAt(rs.getTimestamp("created_at"));
                post.setAttachment(rs.getString("attachment"));
                post.setViews(rs.getInt("views"));
                post.setReplyCount(rs.getInt("reply_count"));
                post.setUsername(rs.getString("username")); // 设置用户名
                post.setPinned(rs.getBoolean("pinned")); // 设置置顶状态
                posts.add(post);
            }
        }
        return posts;
    }

    @Override
    public Post getPostById(int id) throws SQLException {
        String sql = "SELECT p.*, u.username, COUNT(r.id) as reply_count FROM posts p " +
                "JOIN users u ON p.user_id = u.id " +
                "LEFT JOIN replies r ON p.id = r.post_id " +
                "WHERE p.id = ? GROUP BY p.id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Post post = new Post();
                post.setId(rs.getInt("id"));
                post.setTitle(rs.getString("title"));
                post.setContent(rs.getString("content"));
                post.setUserId(rs.getInt("user_id"));
                post.setBoardId(rs.getInt("board_id"));
                post.setCreatedAt(rs.getTimestamp("created_at"));
                post.setAttachment(rs.getString("attachment"));
                post.setViews(rs.getInt("views"));
                post.setReplyCount(rs.getInt("reply_count"));
                post.setUsername(rs.getString("username")); // 设置用户名
                post.setPinned(rs.getBoolean("pinned")); // 设置置顶状态
                return post;
            }
        }
        return null;
    }

    @Override
    public List<Post> searchPosts(String keyword) throws SQLException {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT p.*, u.username FROM posts p " +
                "JOIN users u ON p.user_id = u.id " +
                "WHERE p.title LIKE ? OR p.content LIKE ? ORDER BY p.pinned DESC, p.created_at DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String search = "%" + keyword + "%";
            pstmt.setString(1, search);
            pstmt.setString(2, search);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getInt("id"));
                post.setTitle(rs.getString("title"));
                post.setContent(rs.getString("content"));
                post.setUserId(rs.getInt("user_id"));
                post.setBoardId(rs.getInt("board_id"));
                post.setCreatedAt(rs.getTimestamp("created_at"));
                post.setAttachment(rs.getString("attachment"));
                post.setViews(rs.getInt("views"));
                post.setUsername(rs.getString("username")); // 设置用户名
                post.setPinned(rs.getBoolean("pinned")); // 设置置顶状态
                posts.add(post);
            }
        }
        return posts;
    }

    @Override
    public int countPostsByBoardId(int boardId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM posts WHERE board_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, boardId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    @Override
    public void incrementViews(int id) throws SQLException {
        String sql = "UPDATE posts SET views = views + 1 WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    // 实现置顶相关方法
    @Override
    public void pinPost(int id) throws SQLException {
        String sql = "UPDATE posts SET pinned = TRUE WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    @Override
    public void unpinPost(int id) throws SQLException {
        String sql = "UPDATE posts SET pinned = FALSE WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    @Override
    public boolean isPostPinned(int id) throws SQLException {
        String sql = "SELECT pinned FROM posts WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("pinned");
            }
        }
        return false;
    }
}