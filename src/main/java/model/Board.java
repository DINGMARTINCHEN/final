package model;

/**
 * 版块实体类
 * 对应数据库中的 boards 表
 */
public class Board {
    private int id;
    private String name;        // 版块名称
    private String description; // 版块描述

    // Getter 和 Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}