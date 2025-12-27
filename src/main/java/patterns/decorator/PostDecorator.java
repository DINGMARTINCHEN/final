package patterns.decorator;

import model.Post;

/**
 * 抽象装饰器类
 */
public abstract class PostDecorator extends Post {
    protected Post decoratedPost;

    public PostDecorator(Post decoratedPost) {
        this.decoratedPost = decoratedPost;
    }

    @Override
    public String getContent() {
        return decoratedPost.getContent();
    }
}