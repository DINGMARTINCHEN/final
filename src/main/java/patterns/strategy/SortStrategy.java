// 设计模式3：策略模式 - 帖子排序
// src/patterns/strategy/SortStrategy.java
package patterns.strategy;

import model.Post;
import java.util.List;

/**
 * 排序策略接口
 */
public interface SortStrategy {
    void sort(List<Post> posts);  // 对帖子列表排序
}