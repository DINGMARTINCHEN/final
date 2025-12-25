// 设计模式2：观察者模式
//定义对象间一对多依赖，当主题状态变化时，所有观察者自动收到通知。
package patterns.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 主题（被观察者）抽象类
 */
public class Subject {
    private List<Observer> observers = new ArrayList<>();

    /** 添加观察者 */
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /** 移除观察者 */
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /** 通知所有观察者 */
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}