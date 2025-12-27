package patterns.strategy;

import model.Post;
import java.util.List;

/**
 * 按时间倒序排序（最新在前）
 */
public class SortByLikes implements SortStrategy {
    @Override
    public void sort(List<Post> posts) {
        posts.sort((p1, p2) -> Integer.compare(p2.getLikeCount(), p1.getLikeCount()));
    }
}