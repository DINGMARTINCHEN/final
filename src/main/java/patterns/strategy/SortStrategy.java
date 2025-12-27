package patterns.strategy;

import model.Post;
import java.util.List;

/**
 * 排序策略接口
 */
public interface SortStrategy {
    void sort(List<Post> posts);  // 对帖子列表排序
}