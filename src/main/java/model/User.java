package model;

/**
 * 用户实体类
 * 对应数据库中的 users 表
 */
public class User {
    private int id;          // 用户ID
    private String username; // 用户名
    private String password; // 密码（明文存储，仅用于教学）
    private String role;     // 角色：admin、moderator、user
    private String email;    // 邮箱

    // Getter 和 Setter 方法
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}