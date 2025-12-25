// 新增 SortByViews.java（按浏览量排序）
// src/patterns/strategy/SortByViews.java
package patterns.strategy;

import model.Post;
import java.util.List;

/**
 * 按浏览量倒序排序策略
 */
public class SortByViews implements SortStrategy {
    @Override
    public void sort(List<Post> posts) {
        posts.sort((p1, p2) -> Integer.compare(p2.getViews(), p1.getViews()));
    }
}