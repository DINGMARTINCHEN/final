package dao;

import model.Reply;
import java.sql.SQLException;
import java.util.List;

public interface ReplyDAO {
    void addReply(Reply reply) throws SQLException;
    void deleteReply(int id) throws SQLException;
    List<Reply> getRepliesByPostId(int postId) throws SQLException;
    int countRepliesByPostId(int postId) throws SQLException;
}