package patterns.observer;

public class UserObserver implements Observer {
    private String username;

    public UserObserver(String username) {
        this.username = username;
    }

    @Override
    public void update(String message) {
        System.out.println(username + " 收到通知：" + message);
    }
}