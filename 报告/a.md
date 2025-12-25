# 基于Java GUI的校园论坛系统设计报告

## 学院名称 信息工程学院（大数据学院）
## 专业名称 软件工程
## 所属学期 2025-2026第1学期
## 所属课程 软件设计模式与体系结构
## 课题名称 基于Java GUI的校园论坛系统
## 学号
## 姓名
## 班级
## 任课教师 王逢州、王海洋
## 起止日期 2025.11.26~2025.12.15


## 1 系统分析

### 1.1 设计目的
本项目旨在设计并实现一个基于Java GUI的校园论坛系统，为校园师生提供一个便捷的信息交流平台。通过该系统，用户可以发布帖子、浏览帖子、回复内容、点赞互动等，实现校园内的知识共享与信息传递。同时，本项目通过应用多种软件设计模式（如访问者模式、工厂模式、策略模式等），加深对软件设计原则和设计模式的理解，提升系统的可扩展性、可维护性和复用性，为后续功能迭代奠定良好的架构基础。

### 1.2 系统需求描述及分析
本系统主要满足以下功能需求：
1. **用户管理**：支持用户注册、登录及个人信息管理功能，确保用户身份的合法性与唯一性。
2. **帖子管理**：实现帖子的发布、查看、编辑、删除功能，支持按版块（技术支持、学习交流等）分类管理，同时提供置顶、公告等特殊类型帖子的处理。
3. **互动功能**：用户可对帖子进行点赞、取消点赞操作，支持查看自己点赞的帖子，实现用户间的互动交流。
4. **搜索与排序**：提供帖子搜索功能，支持按发布时间、浏览量、点赞数等多种方式排序，方便用户快速定位所需内容。
5. **界面展示**：通过GUI界面直观展示帖子列表、详情及用户操作入口，保证操作的便捷性与视觉的友好性。

从系统架构角度分析，采用MVC模式实现数据层、控制层与视图层的分离，使各模块职责清晰，便于独立开发与测试。

### 1.3 所使用设计模式列表

| 序号 | 功能模块         | 使用设计模式       | 备注                                   |
|------|------------------|--------------------|----------------------------------------|
| 1    | 帖子点赞功能     | 访问者模式         | 动态为帖子添加点赞功能，不修改Post类   |
| 2    | 帖子创建功能     | 工厂模式           | 根据帖子类型（置顶、公告、标准）创建不同帖子 |
| 3    | 帖子排序功能     | 策略模式           | 支持按时间、浏览量、点赞数等多种排序策略 |
| 4    | 帖子附件功能     | 装饰器模式         | 为帖子动态添加附件功能，扩展帖子属性   |
| 5    | 系统整体架构     | MVC模式            | 分离模型、视图、控制器，实现关注点分离 |
| 6    | 状态通知功能     | 观察者模式         | 管理观察者，支持添加/移除观察者实现状态同步 |


## 2 功能模块设计

### 2.1 帖子点赞功能模块

#### 2.1.1 功能说明及描述
该模块实现用户对帖子的点赞与取消点赞功能，支持记录用户的点赞状态，实时更新帖子的点赞数量，并在界面上反馈操作结果。用户可通过双击帖子查看详情时进行点赞操作，也可在"我的点赞"中查看自己点赞过的帖子。

#### 2.1.2 设计模式分析及选择
本模块采用**访问者模式**实现。选择原因如下：
- 点赞功能属于帖子的"操作"而非"属性"，若直接在Post类中添加点赞方法，会导致Post类职责过重，违反单一职责原则。
- 访问者模式可将点赞逻辑封装在独立的访问者类中，使Post类与操作逻辑解耦，符合开闭原则（添加新操作无需修改Post类）。
- 便于扩展其他类似操作（如收藏、举报等），只需新增访问者类即可，无需修改核心模型。

#### 2.1.3 UML类图的设计和绘制
访问者模式的主要角色包括：
- **抽象元素（Element）**：Post类，定义接受访问者的方法（accept）。
- **具体元素（ConcreteElement）**：实际的帖子类，实现accept方法，调用访问者的访问方法。
- **抽象访问者（Visitor）**：PostVisitor接口，声明对元素的访问方法（visit）。
- **具体访问者（ConcreteVisitor）**：LikeVisitor类，实现点赞逻辑。

本系统中，Post类作为抽象元素，LikeVisitor作为具体访问者，通过accept方法接收访问者并执行点赞操作。

#### 2.1.4 功能实现
核心代码如下：
```java
// 访问者接口
public interface PostVisitor {
    String visit(Post post);
}

// 点赞访问者实现
public class LikeVisitor implements PostVisitor {
    private int userId;
    
    public LikeVisitor(int userId) {
        this.userId = userId;
    }
    
    @Override
    public String visit(Post post) {
        LikeController likeController = new LikeController();
        if (likeController.hasLiked(userId, post.getId())) {
            // 取消点赞
            likeController.unlike(userId, post.getId());
            post.setLikeCount(post.getLikeCount() - 1);
            return "已取消点赞";
        } else {
            // 执行点赞
            likeController.like(userId, post.getId());
            post.setLikeCount(post.getLikeCount() + 1);
            return "点赞成功";
        }
    }
}

// 帖子类（元素）
public class Post {
    private int id;
    private String title;
    private int likeCount;
    // 其他属性及getter/setter...
    
    public String accept(PostVisitor visitor) {
        return visitor.visit(this);
    }
}

// 测试代码（VisitorTest.java）
public static void main(String[] args) {
    // 创建测试帖子和用户
    Post post = new Post();
    post.setId(1);
    post.setTitle("Java设计模式学习心得");
    
    User user = new User();
    user.setId(1);
    
    // 第一次点赞
    PostVisitor likeVisitor1 = new LikeVisitor(user.getId());
    String result1 = post.accept(likeVisitor1);
    System.out.println("第一次操作结果：" + result1); // 点赞成功
    
    // 第二次操作（取消点赞）
    PostVisitor likeVisitor2 = new LikeVisitor(user.getId());
    String result2 = post.accept(likeVisitor2);
    System.out.println("第二次操作结果：" + result2); // 已取消点赞
}
```


### 2.2 帖子创建功能模块

#### 2.2.1 功能说明及描述
该模块实现不同类型帖子（标准帖子、置顶帖子、公告帖子）的创建功能，根据用户选择的帖子类型，生成对应的帖子实例并保存到系统中。管理员可创建置顶和公告帖子，普通用户仅能创建标准帖子。

#### 2.2.2 设计模式分析及选择
本模块采用**工厂模式**实现。选择原因如下：
- 帖子类型多样（标准、置顶、公告），且可能扩展新类型，工厂模式可封装对象创建逻辑，降低客户端与具体类的耦合。
- 通过工厂选择器（PostFactorySelector）根据用户权限和帖子类型选择对应的工厂，符合单一职责原则（每个工厂专注于创建一种类型的帖子）。
- 客户端无需知道具体帖子类的名称，只需通过工厂接口获取实例，简化了对象创建过程。

#### 2.2.3 UML类图的设计和绘制
工厂模式的主要角色包括：
- **抽象工厂（Abstract Factory）**：PostFactory接口，定义创建帖子的方法（createPost）。
- **具体工厂（Concrete Factory）**：StandardPostFactory、PinnedPostFactory等，实现具体帖子的创建逻辑。
- **产品（Product）**：Post类及子类，代表工厂创建的对象。
- **工厂选择器（Factory Selector）**：PostFactorySelector，根据条件选择合适的工厂。

本系统中，通过工厂选择器根据用户权限和帖子类型返回对应工厂，再由工厂创建具体帖子实例。

#### 2.2.4 功能实现
核心代码如下：
```java
// 抽象工厂接口
public interface PostFactory {
    Post createPost(String title, String content, int userId, int boardId, String attachment);
}

// 标准帖子工厂
public class StandardPostFactory implements PostFactory {
    @Override
    public Post createPost(String title, String content, int userId, int boardId, String attachment) {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setUserId(userId);
        post.setBoardId(boardId);
        post.setAttachment(attachment);
        post.setPinned(false);
        post.setCreatedAt(new Date());
        return post;
    }
}

// 置顶帖子工厂
public class PinnedPostFactory implements PostFactory {
    @Override
    public Post createPost(String title, String content, int userId, int boardId, String attachment) {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setUserId(userId);
        post.setBoardId(boardId);
        post.setAttachment(attachment);
        post.setPinned(true); // 置顶帖子特殊属性
        post.setCreatedAt(new Date());
        return post;
    }
}

// 工厂选择器
public class PostFactorySelector {
    public static PostFactory getFactory(User user, String type) {
        // 权限校验：普通用户不能创建置顶/公告帖子
        if (("pinned".equals(type) || "announcement".equals(type)) && !user.isAdmin()) {
            throw new IllegalArgumentException("权限不足，无法创建该类型帖子");
        }
        
        switch (type) {
            case "pinned":
                return new PinnedPostFactory();
            case "announcement":
                return new AnnouncementPostFactory();
            default:
                return new StandardPostFactory();
        }
    }
}

// 控制器中调用（PostController.java）
public boolean createPost(String title, String content, int userId, int boardId,
                          String attachment, String postType, User user) {
    try {
        String factoryType = convertPostType(postType);
        PostFactory factory = PostFactorySelector.getFactory(user, factoryType);
        Post post = factory.createPost(title, content, userId, boardId, attachment);
        return postService.createPost(post, postType, userId);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "发帖失败：" + e.getMessage());
        return false;
    }
}
```


### 2.3 帖子排序功能模块

#### 2.3.1 功能说明及描述
该模块实现帖子列表的排序功能，支持按发布时间（默认）、浏览量、点赞数三种排序方式，用户可通过界面按钮切换排序策略，排序结果实时更新到表格中。置顶帖子始终优先显示在列表前端。

#### 2.3.2 设计模式分析及选择
本模块采用**策略模式**实现。选择原因如下：
- 排序算法多样（按时间、浏览量、点赞数），且可能扩展新算法，策略模式可将每种算法封装为独立策略，便于切换与扩展。
- 客户端（MainForumView）通过统一接口与策略交互，无需关心具体排序逻辑，降低了代码耦合。
- 符合开闭原则，新增排序方式只需添加新策略类，无需修改现有代码。

#### 2.3.3 UML类图的设计和绘制
策略模式的主要角色包括：
- **抽象策略（Strategy）**：SortStrategy接口，定义排序方法（sort）。
- **具体策略（Concrete Strategy）**：SortByDate、SortByViews、SortByLikes，实现具体排序逻辑。
- **环境（Context）**：PostSorter类，持有策略引用，负责调用策略执行排序。

本系统中，PostSorter作为环境类，根据用户选择的排序方式切换具体策略，实现帖子列表的动态排序。

#### 2.3.4 功能实现
核心代码如下：
```java
// 抽象策略接口
public interface SortStrategy {
    void sort(List<Post> posts);
}

// 按时间排序策略
public class SortByDate implements SortStrategy {
    @Override
    public void sort(List<Post> posts) {
        posts.sort((p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()));
    }
}

// 按浏览量排序策略
public class SortByViews implements SortStrategy {
    @Override
    public void sort(List<Post> posts) {
        posts.sort((p1, p2) -> Integer.compare(p2.getViews(), p1.getViews()));
    }
}

// 环境类
public class PostSorter {
    private SortStrategy strategy;
    
    public void setStrategy(SortStrategy strategy) {
        this.strategy = strategy;
    }
    
    public void sortPosts(List<Post> posts) {
        if (strategy != null) {
            strategy.sort(posts);
        }
    }
}

// 控制器中调用（PostController.java）
public void sortAndDisplayPosts(MainForumView mainForumView) {
    if (mainForumView.currentPosts.isEmpty()) return;

    if ("date".equals(mainForumView.currentSort)) {
        mainForumView.postSorter.setStrategy(new SortByDate());
        mainForumView.postSorter.sortPosts(mainForumView.currentPosts);
    } else if ("views".equals(mainForumView.currentSort)) {
        mainForumView.postSorter.setStrategy(new SortByViews());
        mainForumView.postSorter.sortPosts(mainForumView.currentPosts);
    } else if ("likes".equals(mainForumView.currentSort)) {
        mainForumView.currentPosts.sort((p1, p2) -> Integer.compare(p2.getLikeCount(), p1.getLikeCount()));
    }

    mainForumView.postController.refreshPostTable(mainForumView);
}
```


### 2.4 帖子附件功能模块

#### 2.4.1 功能说明及描述
该模块为帖子提供附件功能，支持在发布帖子时添加图片、文档等附件，用户查看帖子详情时可下载或预览附件。附件功能作为帖子的扩展属性，不影响帖子的核心功能（标题、内容等）。

#### 2.4.2 设计模式分析及选择
本模块采用**装饰器模式**实现。选择原因如下：
- 附件功能是帖子的可选扩展，装饰器模式可在不修改Post类的前提下动态为其添加附件功能，符合开闭原则。
- 支持为帖子添加多个附件（如同时添加图片和文档），通过装饰器嵌套实现功能叠加。
- 装饰器与被装饰者实现相同接口，客户端可透明使用装饰后的对象，无需区分处理。

#### 2.4.3 UML类图的设计和绘制
装饰器模式的主要角色包括：
- **抽象组件（Component）**：Post类，定义帖子的核心接口（如getTitle、getContent）。
- **具体组件（Concrete Component）**：基础帖子类，实现核心功能。
- **抽象装饰器（Decorator）**：PostDecorator类，持有组件引用，实现与组件相同的接口。
- **具体装饰器（Concrete Decorator）**：AttachmentDecorator类，添加附件功能。

本系统中，AttachmentDecorator作为具体装饰器，在保留帖子原有功能的基础上，新增附件相关方法。

#### 2.4.4 功能实现
核心代码如下：
```java
// 抽象组件（帖子）
public interface Post {
    String getTitle();
    String getContent();
    int getId();
    // 其他核心方法...
}

// 具体组件（基础帖子）
public class BasePost implements Post {
    private int id;
    private String title;
    private String content;
    // 其他属性及实现...
    
    @Override
    public String getTitle() {
        return title;
    }
    
    @Override
    public String getContent() {
        return content;
    }
}

// 抽象装饰器
public abstract class PostDecorator implements Post {
    protected Post decoratedPost;
    
    public PostDecorator(Post post) {
        this.decoratedPost = post;
    }
    
    @Override
    public String getTitle() {
        return decoratedPost.getTitle();
    }
    
    @Override
    public String getContent() {
        return decoratedPost.getContent();
    }
    
    @Override
    public int getId() {
        return decoratedPost.getId();
    }
}

// 具体装饰器（附件装饰）
public class AttachmentDecorator extends PostDecorator {
    private String attachment; // 附件路径或信息
    
    public AttachmentDecorator(Post post, String attachment) {
        super(post);
        this.attachment = attachment;
    }
    
    // 新增附件相关方法
    public String getAttachment() {
        return attachment;
    }
    
    public void downloadAttachment() {
        // 下载逻辑实现
        System.out.println("下载附件：" + attachment);
    }
    
    // 重写其他方法，确保装饰器正常工作
    @Override
    public String getTitle() {
        return decoratedPost.getTitle();
    }
}
```


## 3 系统实现

### 3.1 系统体系结构
本系统采用**MVC（Model-View-Controller）** 设计模式，实现数据、界面与业务逻辑的分离，具体划分如下：

- **Model（模型层）**：负责数据管理和业务逻辑，包括实体类（Post、User、Reply等）和服务类（PostService、LikeService等）。模型层独立于界面，专注于数据的存储、验证和处理。
- **View（视图层）**：负责用户界面展示，包括MainForumView、RegisterView、PostCreateView等JFrame组件。视图层仅关注界面渲染，不处理业务逻辑，通过事件监听与控制器交互。
- **Controller（控制层）**：作为模型与视图的中间层，负责接收视图的用户输入，调用模型层处理业务逻辑，并将结果反馈给视图。包括PostController、ReplyController、LikeController等。

MVC模式使系统各层职责清晰，便于团队协作开发，同时提高了代码的复用性和可维护性（如修改界面无需变动业务逻辑）。

### 3.2 帖子管理功能模块设计及实现

#### （1）前端设计（View层）
由MainForumView类实现，主要包含：
- 顶部面板：版块选择下拉框、搜索框、排序按钮（按时间/浏览量/点赞数）。
- 中间表格：展示帖子ID、标题、作者、发布时间等信息，双击可查看详情。
- 底部面板：刷新、发新帖、删除帖子、个人信息等操作按钮。

核心代码片段：
```java
// MainForumView.java 界面初始化
private void initializeUI() {
    setTitle("校园论坛 - 主界面 (" + currentUser.getUsername() + ")");
    setSize(1100, 750);
    setLocationRelativeTo(null);

    JPanel mainPanel = new JPanel(new BorderLayout());

    // 顶部面板（搜索、排序等）
    JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    searchField = new JTextField(20);
    JButton searchButton = new JButton("搜索");
    searchButton.addActionListener(e -> postController.searchPosts(searchField.getText().trim(), postController, this));
    
    // 版块选择下拉框
    boardComboBox = new JComboBox<>(new String[]{"全部版块", "技术支持", "学习交流", "休闲娱乐", "校园生活"});
    boardComboBox.addActionListener(e -> {
        // 版块切换逻辑
    });
    
    // 排序按钮
    JButton sortByDateButton = new JButton("按时间排序");
    sortByDateButton.addActionListener(e -> {
        currentSort = "date";
        postController.sortAndDisplayPosts(this);
    });
    // 其他组件添加...
    
    mainPanel.add(topPanel, BorderLayout.NORTH);
    add(mainPanel);
}
```

#### （2）控制层设计（Controller层）
由PostController类实现，主要职责：
- 处理帖子的增删改查逻辑（如createPost、deletePost、getPostDetail）。
- 协调排序策略的切换与执行（sortAndDisplayPosts方法）。
- 接收视图层的事件（如搜索、删除请求），调用模型层处理并更新视图。

核心代码片段：
```java
// PostController.java 加载版块帖子
public List<Post> getBoardPosts(int boardId, int page, int pageSize) {
    try {
        return postService.getPostsByBoard(boardId, page, pageSize);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "加载帖子失败：" + e.getMessage());
        return null;
    }
}

// 刷新帖子表格
public void refreshPostTable(MainForumView mainForumView) {
    mainForumView.tableModel.setRowCount(0);
    mainForumView.rowToPostMap.clear();

    List<Post> postsToDisplay = mainForumView.isSearching ? mainForumView.searchResults : mainForumView.currentPosts;
    if (postsToDisplay == null || postsToDisplay.isEmpty()) {
        mainForumView.paginationLabel.setText("没有找到相关帖子");
        return;
    }

    // 分离置顶帖子和普通帖子并排序
    // ...（排序逻辑）
    
    // 更新表格数据
    int rowIndex = 0;
    for (Post post : pinnedPosts) {
        // 表格行数据添加
        mainForumView.rowToPostMap.put(rowIndex, post);
        rowIndex++;
    }
    // 普通帖子处理...
}
```

#### （3）模型层设计（Model层）
- 实体类：Post类封装帖子属性（id、title、content、likeCount等）及getter/setter方法。
- 服务类：PostService类负责与数据访问层交互，实现帖子的持久化操作（如数据库CRUD）。
- 数据访问：PostDAOImpl类实现数据库操作，如置顶帖子的SQL执行。

核心代码片段：
```java
// Post.java 实体类
public class Post {
    private int id;
    private String title;
    private String content;
    private Date createdAt;
    private int views;
    private int likeCount;
    private boolean isPinned;
    // 其他属性及getter/setter...
}

// PostDAOImpl.java 数据访问
@Override
public void pinPost(int id) throws SQLException {
    String sql = "UPDATE posts SET pinned = TRUE WHERE id = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
    }
}
```


### 3.3 用户注册功能模块设计及实现

#### （1）前端设计（View层）
由RegisterView类实现，提供用户注册表单，包含用户名、密码、确认密码、邮箱输入框及注册/取消按钮。通过事件监听捕获用户输入，调用控制器进行验证。

核心代码片段：
```java
// RegisterView.java
public class RegisterView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField emailField;
    private JButton registerButton;
    private JButton cancelButton;
    private UserController controller;

    public RegisterView(UserController controller) {
        this.controller = controller;
        setTitle("校园论坛系统 - 注册新用户");
        setSize(400, 350);
        setLayout(new GridLayout(6, 2, 10, 10));

        add(new JLabel("用户名："));
        usernameField = new JTextField();
        add(usernameField);
        // 其他输入框添加...

        registerButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            String email = emailField.getText().trim();

            // 输入验证
            if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(RegisterView.this, "请填写完整信息！");
                return;
            }
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(RegisterView.this, "两次密码不一致！");
                return;
            }

            controller.registerUser(username, password, email);
            JOptionPane.showMessageDialog(RegisterView.this, "注册成功！请返回登录");
            dispose();
        });
    }
}
```

#### （2）控制层设计（Controller层）
由UserController类实现，接收注册视图的用户数据，调用模型层进行用户注册逻辑处理（如查重、加密存储），并返回处理结果。

#### （3）模型层设计（Model层）
- 实体类：User类封装用户信息（id、username、password、email等）。
- 服务类：UserService类实现用户注册业务逻辑（如密码加密、数据库插入）。


## 4 项目设计小结

通过本次基于Java GUI的校园论坛系统设计与实现，我深刻体会到设计模式在软件开发中的重要作用。设计模式并非抽象的理论，而是解决特定问题的成熟方案：访问者模式使点赞功能与帖子模型解耦，便于扩展新的互动操作；工厂模式简化了不同类型帖子的创建过程，提高了代码的复用性；策略模式让排序功能的切换更加灵活，新增排序方式无需修改核心逻辑。这些模式的应用使系统架构更清晰，代码可维护性显著提升，也为后续功能迭代（如添加收藏、分享功能）奠定了良好基础。

在系统实现过程中，MVC模式的应用使数据、界面与业务逻辑分离，便于团队协作与单元测试。视图层专注于用户交互，控制层协调各模块工作，模型层处理核心业务，这种职责划分有效降低了代码耦合度。同时，通过解决设计模式应用中的细节问题（如装饰器模式的方法重写、策略模式的动态切换），我对"开闭原则""单一职责原则"等设计原则有了更直观的理解。

总体而言，本次项目达成了课程目标：不仅掌握了多种设计模式的应用场景与实现方法，还理解了软件体系结构设计的重要性。未来开发中，我将继续深入学习设计模式，结合实际需求灵活选用，提升软件设计的专业性与工程性。


## 《软件设计模式与体系结构》项目报告评价

| 序号 | 内容               | 目标分值 | 评价依据                                                                                                                                 | 评价意见 | 报告得分 |
|------|--------------------|----------|------------------------------------------------------------------------------------------------------------------------------------------|----------|----------|
| 1    | 系统描述及分析     | 10       | 系统需求分析是否充分、合理、完整                                                                                                         |          |          |
| 2    | 设计模式1综合应用  | 15       | （1）选择所用模式的原因及必要性；（2）对UML中各个角色的含义描述是否符合题意；（3）程序是否较好地按要求完成各功能的实现                     |          |          |
|      | 设计模式2综合应用  | 15       | 同上                                                                                                                                     |          |          |
|      | 设计模式3综合应用  | 15       | 同上                                                                                                                                     |          |          |
|      | 设计模式4综合应用  | 15       | 同上                                                                                                                                     |          |          |
| 3    | 体系结构设计       | 10       | 体系结构选择应用的说明，所用MVC各个角色的含义描述是否准确；本系统设计的组件中M、V、C各是哪些描述是否清楚                                  |          |          |
|      | 体系结构实现       | 20       | 体系结构应用及各功能模块实现（包括前端、控制层、模型层）描述是否清楚；运行效果情况如何                                                       |          |          |
|      | 项目报告得分       | 100      |                                                                                                                                          |          |          |