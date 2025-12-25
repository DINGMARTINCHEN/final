package dao.impl;

import dao.ReplyDAO;
import db.DatabaseConnection;
import model.Reply;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date; // 添加java.util.Date的导入

/**
 * ReplyDAO 接口的实现类
 * 负责回复相关的数据库操作
 */
public class ReplyDAOImpl implements ReplyDAO {

    @Override
    public void addReply(Reply reply) throws SQLException {
        String sql = "INSERT INTO replies (content, post_id, user_id) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, reply.getContent());
            pstmt.setInt(2, reply.getPostId());
            pstmt.setInt(3, reply.getUserId());
            pstmt.executeUpdate();

            // 获取自增ID
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                reply.setId(rs.getInt(1));
                reply.setCreatedAt(new Date()); // 设置创建时间
            }
        }
    }

    @Override
    public void deleteReply(int id) throws SQLException {
        String sql = "DELETE FROM replies WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    @Override
    public List<Reply> getRepliesByPostId(int postId) throws SQLException {
        List<Reply> replies = new ArrayList<>();
        String sql = "SELECT r.*, u.username FROM replies r " +
                "JOIN users u ON r.user_id = u.id " +
                "WHERE r.post_id = ? ORDER BY r.created_at ASC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, postId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Reply reply = new Reply();
                reply.setId(rs.getInt("id"));
                reply.setContent(rs.getString("content"));
                reply.setPostId(rs.getInt("post_id"));
                reply.setUserId(rs.getInt("user_id"));
                reply.setCreatedAt(rs.getTimestamp("created_at"));
                reply.setUsername(rs.getString("username")); // 设置用户名
                replies.add(reply);
            }
        }
        return replies;
    }

    @Override
    public int countRepliesByPostId(int postId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM replies WHERE post_id = ?";
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
}