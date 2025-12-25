// src/patterns/strategy/PostSorter.java
//将算法封装成独立策略类，上下文动态切换策略。
package patterns.strategy;

import model.Post;
import java.util.List;

/**
 * 上下文类：持有当前排序策略
 */
public class PostSorter {
    private SortStrategy strategy;

    public void setStrategy(SortStrategy strategy) {
        this.strategy = strategy;
    }

    public void sortPosts(List<Post> posts) {
        if (strategy != null) {
            strategy.sort(posts);
        }
    }
}