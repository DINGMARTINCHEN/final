package patterns.observer;

/**
 * 具体的主题：帖子更新主题
 */
public class PostSubject extends Subject {
    /** 当帖子更新时通知所有关注者 */
    public void postUpdated(String postTitle) {
        notifyObservers("帖子《" + postTitle + "》有变动！");
    }
}