package dao.impl;

import dao.LikeDAO;
import db.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 点赞数据访问实现类
 */
public class LikeDAOImpl implements LikeDAO {

    @Override
    public void addLike(int userId, int postId) throws SQLException {
        String sql = "INSERT INTO post_likes (user_id, post_id, created_at) VALUES (?, ?, NOW())";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, postId);
            pstmt.executeUpdate();
        }
    }

    @Override
    public void cancelLike(int userId, int postId) throws SQLException {
        String sql = "DELETE FROM post_likes WHERE user_id = ? AND post_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, postId);
            pstmt.executeUpdate();
        }
    }

    @Override
    public boolean hasLiked(int userId, int postId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM post_likes WHERE user_id = ? AND post_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, postId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    @Override
    public int getLikeCount(int postId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM post_likes WHERE post_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, postId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    @Override
    public List<Integer> getLikedPostsByUser(int userId) throws SQLException {
        List<Integer> likedPosts = new ArrayList<>();
        String sql = "SELECT post_id FROM post_likes WHERE user_id = ? ORDER BY created_at DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                likedPosts.add(rs.getInt("post_id"));
            }
        }
        return likedPosts;
    }
}