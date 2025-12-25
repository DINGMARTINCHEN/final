// src/service/ReplyService.java
package service;

import dao.ReplyDAO;
import dao.impl.ReplyDAOImpl;
import model.Reply;
import service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

/**
 * 回复业务逻辑服务层
 */
public class ReplyService {
    private ReplyDAO replyDAO;

    public ReplyService() {
        this.replyDAO = new ReplyDAOImpl();
    }

    /**
     * 添加回复
     */
    public boolean addReply(Reply reply) {
        try {
            replyDAO.addReply(reply);
            return true;
        } catch (SQLException e) {
            throw new ServiceException("添加回复失败", e);
        }
    }

    /**
     * 获取帖子的回复
     */
    public List<Reply> getRepliesByPost(int postId) {
        try {
            return replyDAO.getRepliesByPostId(postId);
        } catch (SQLException e) {
            throw new ServiceException("获取回复失败", e);
        }
    }

    /**
     * 删除回复
     */
    public boolean deleteReply(int replyId) {
        try {
            replyDAO.deleteReply(replyId);
            return true;
        } catch (SQLException e) {
            throw new ServiceException("删除回复失败", e);
        }
    }
}