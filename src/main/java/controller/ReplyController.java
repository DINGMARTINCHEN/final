package controller;

import service.ReplyService;
import model.Reply;

import javax.swing.*;
import java.util.List;

/**
 * 回复控制器 - 处理回复相关的业务逻辑
 */
public class ReplyController {
    private ReplyService replyService;

    public ReplyController() {
        this.replyService = new ReplyService();
    }

    /**
     * 添加回复
     */
    public boolean addReply(Reply reply) {
        try {
            return replyService.addReply(reply);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "发表回复失败：" + e.getMessage());
            return false;
        }
    }

    /**
     * 获取帖子回复
     */
    public List<Reply> getPostReplies(int postId) {
        try {
            return replyService.getRepliesByPost(postId);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "获取回复失败：" + e.getMessage());
            return null;
        }
    }

    /**
     * 删除回复
     */
    public boolean deleteReply(int replyId) {
        try {
            return replyService.deleteReply(replyId);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "删除回复失败：" + e.getMessage());
            return false;
        }
    }
}