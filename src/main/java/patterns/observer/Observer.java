package patterns.observer;

/**
 * 观察者接口
 */
public interface Observer {
    void update(String message);  // 收到通知时调用
}