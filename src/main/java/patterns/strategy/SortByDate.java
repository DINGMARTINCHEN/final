// src/patterns/strategy/SortByDate.java
package patterns.strategy;

import model.Post;
import java.util.List;

/**
 * 按时间倒序排序（最新在前）
 */
public class SortByDate implements SortStrategy {
    @Override
    public void sort(List<Post> posts) {
        posts.sort((p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()));
    }
}