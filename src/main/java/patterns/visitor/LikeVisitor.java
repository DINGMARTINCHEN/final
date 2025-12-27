package patterns.visitor;

import dao.impl.LikeDAOImpl;
import model.Post;

/**
 * 点赞访问者
 * 实现点赞功能
 */
public class LikeVisitor implements PostVisitor {
    private int userId;
    private LikeDAOImpl likeDAO;

    public LikeVisitor(int userId) {
        this.userId = userId;
        this.likeDAO = new LikeDAOImpl();
    }

    @Override
    public String visit(Post post) {
        try {
            // 检查是否已经点赞
            if (likeDAO.hasLiked(userId, post.getId())) {
                // 取消点赞
                likeDAO.cancelLike(userId, post.getId());
                int likeCount = likeDAO.getLikeCount(post.getId());
                return String.format("已取消对帖子《%s》的点赞\n当前点赞数：%d",
                        post.getTitle(), likeCount);
            } else {
                // 添加点赞
                likeDAO.addLike(userId, post.getId());
                int likeCount = likeDAO.getLikeCount(post.getId());
                return String.format("已成功点赞帖子《%s》\n当前点赞数：%d",
                        post.getTitle(), likeCount);
            }
        } catch (Exception e) {
            return "操作失败：" + e.getMessage();
        }
    }

    @Override
    public String getOperationName() {
        return "点赞功能";
    }

    @Override
    public String getOperationDescription() {
        return "为帖子添加点赞/取消点赞功能";
    }
}