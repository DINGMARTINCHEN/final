// 设计模式4：装饰器模式 - 为帖子添加附件功能
//动态为对象添加功能，而不修改原类，通过层层包装。
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