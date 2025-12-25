// src/controller/PostController.java
package controller;

import patterns.strategy.SortByDate;
import patterns.strategy.SortByViews;
import service.PostService;
import model.Post;
import model.User;
import patterns.factory.PostFactory;
import patterns.factory.PostFactorySelector;
import view.MainForumView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * 帖子控制器 - 处理帖子相关的业务逻辑
 */
public class PostController {
    private PostService postService;

    public PostController() {
        this.postService = new PostService();
    }

    /**
     * 创建新帖子
     */
    public boolean createPost(String title, String content, int userId, int boardId,
                              String attachment, String postType, User user) {
        try {
            // 使用工厂模式创建帖子
            String factoryType = convertPostType(postType);
            PostFactory factory = PostFactorySelector.getFactory(user, factoryType);
            Post post = factory.createPost(title, content, userId, boardId, attachment);

            return postService.createPost(post, postType, userId);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "发帖失败：" + e.getMessage());
            return false;
        }
    }

    /**
     * 获取版块帖子
     */
    public List<Post> getBoardPosts(int boardId, int page, int pageSize) {
        try {
            return postService.getPostsByBoard(boardId, page, pageSize);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "加载帖子失败：" + e.getMessage());
            return null;
        }
    }

    /**
     * 搜索帖子
     */
    public List<Post> searchPosts(String keyword) {
        try {
            return postService.searchPosts(keyword);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "搜索失败：" + e.getMessage());
            return null;
        }
    }

    /**
     * 获取帖子详情
     */
    public Post getPostDetail(int postId) {
        try {
            return postService.getPostById(postId);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "获取帖子详情失败：" + e.getMessage());
            return null;
        }
    }

    /**
     * 更新帖子
     */
    public boolean updatePost(Post post) {
        try {
            return postService.updatePost(post);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "更新帖子失败：" + e.getMessage());
            return false;
        }
    }

    /**
     * 删除帖子
     */
    public boolean deletePost(int postId) {
        try {
            return postService.deletePost(postId);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "删除帖子失败：" + e.getMessage());
            return false;
        }
    }

    /**
     * 置顶/取消置顶
     */
    public boolean togglePinPost(int postId, boolean pin) {
        try {
            return postService.togglePinPost(postId, pin);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "操作失败：" + e.getMessage());
            return false;
        }
    }

    /**
     * 转换帖子类型
     */
    private String convertPostType(String postType) {
        switch (postType) {
            case "置顶帖子":
                return "pinned";
            case "公告帖子":
                return "announcement";
            default:
                return "standard";
        }
    }

    /**
     * 排序并显示帖子
     *
     * @param mainForumView
     */
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

    public void deleteSelectedPost(MainForumView mainForumView) {
        int row = mainForumView.postTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(mainForumView, "请先选择要删除的帖子！");
            return;
        }

        Post post = mainForumView.getPostByTableRow(row);
        if (post == null) {
            JOptionPane.showMessageDialog(mainForumView, "无法找到对应的帖子，请刷新后重试。");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(mainForumView,
                "确认删除帖子《" + post.getTitle() + "》？此操作不可恢复！",
                "删除确认", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = deletePost(post.getId());
            if (success) {
                JOptionPane.showMessageDialog(mainForumView, "删除成功！");
                mainForumView.postController.loadPostsForSelectedBoard(mainForumView);
            }
        }
    }

    // 显示我的点赞帖子功能
    public void showMyLikedPosts(MainForumView mainForumView) {
        List<Integer> likedPostIds = mainForumView.likeController.getLikedPostsByUser(mainForumView.currentUser.getId());
        if (likedPostIds == null || likedPostIds.isEmpty()) {
            JOptionPane.showMessageDialog(mainForumView, "您还没有点赞过任何帖子！");
            return;
        }

        JDialog likesDialog = new JDialog(mainForumView, "我的点赞", true);
        likesDialog.setSize(800, 600);
        likesDialog.setLocationRelativeTo(mainForumView);
        likesDialog.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("我的点赞帖子 (" + likedPostIds.size() + " 个)", JLabel.CENTER);
        titleLabel.setFont(new Font("宋体", Font.BOLD, 18));
        likesDialog.add(titleLabel, BorderLayout.NORTH);

        String[] columns = {"帖子ID", "标题", "点赞时间"};
        DefaultTableModel likesTableModel = new DefaultTableModel(columns, 0);
        JTable likesTable = new JTable(likesTableModel);

        likesTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = likesTable.getSelectedRow();
                    if (row >= 0) {
                        int postId = (int) likesTableModel.getValueAt(row, 0);
                        Post post = getPostDetail(postId);
                        if (post != null) {
                            likesDialog.dispose();
                            mainForumView.openPostDetail(post);
                        }
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(likesTable);
        likesDialog.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton closeButton = new JButton("关闭");
        closeButton.addActionListener(e -> likesDialog.dispose());
        bottomPanel.add(closeButton);
        likesDialog.add(bottomPanel, BorderLayout.SOUTH);

        // 加载数据
        for (Integer postId : likedPostIds) {
            Post post = getPostDetail(postId);
            if (post != null) {
                likesTableModel.addRow(new Object[]{
                        postId,
                        post.getTitle(),
                        "点击查看详情"
                });
            }
        }

        likesDialog.setVisible(true);
    }

    /**
     * 刷新帖子表格显示
     * 建立行索引到帖子对象的映射关系
     *
     * @param mainForumView
     */
    public void refreshPostTable(MainForumView mainForumView) {
        mainForumView.tableModel.setRowCount(0);
        mainForumView.rowToPostMap.clear(); // 清空旧的映射关系

        List<Post> postsToDisplay = mainForumView.isSearching ? mainForumView.searchResults : mainForumView.currentPosts;
        if (postsToDisplay == null || postsToDisplay.isEmpty()) {
            mainForumView.paginationLabel.setText("没有找到相关帖子");
            return;
        }

        // 分离置顶帖子和普通帖子
        List<Post> pinnedPosts = new ArrayList<>();
        List<Post> normalPosts = new ArrayList<>();

        for (Post post : postsToDisplay) {
            if (post.isPinned()) {
                pinnedPosts.add(post);
            } else {
                normalPosts.add(post);
            }
        }

        // 只在非搜索状态下对当前列表进行排序
        if (!mainForumView.isSearching) {
            if ("date".equals(mainForumView.currentSort)) {
                pinnedPosts.sort((p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()));
                normalPosts.sort((p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()));
            } else if ("views".equals(mainForumView.currentSort)) {
                pinnedPosts.sort((p1, p2) -> Integer.compare(p2.getViews(), p1.getViews()));
                normalPosts.sort((p1, p2) -> Integer.compare(p2.getViews(), p1.getViews()));
            } else if ("likes".equals(mainForumView.currentSort)) {
                pinnedPosts.sort((p1, p2) -> Integer.compare(p2.getLikeCount(), p1.getLikeCount()));
                normalPosts.sort((p1, p2) -> Integer.compare(p2.getLikeCount(), p1.getLikeCount()));
            }
        }

        int rowIndex = 0;

        // 先显示置顶帖子
        for (Post post : pinnedPosts) {
            boolean hasLiked = mainForumView.likeController.hasLiked(mainForumView.currentUser.getId(), post.getId());
            String status = hasLiked ? "❤️ 已赞" : "🤍 未赞";

            mainForumView.tableModel.addRow(new Object[]{
                    post.getId(),
                    "[置顶] " + post.getTitle(),
                    post.getUsername(),
                    post.getCreatedAt(),
                    post.getViews(),
                    post.getReplyCount(),
                    post.getLikeCount(),
                    status
            });

            // 建立映射关系
            mainForumView.rowToPostMap.put(rowIndex, post);
            rowIndex++;
        }

        // 再显示普通帖子
        for (Post post : normalPosts) {
            boolean hasLiked = mainForumView.likeController.hasLiked(mainForumView.currentUser.getId(), post.getId());
            String status = hasLiked ? "❤️ 已赞" : "🤍 未赞";

            mainForumView.tableModel.addRow(new Object[]{
                    post.getId(),
                    post.getTitle(),
                    post.getUsername(),
                    post.getCreatedAt(),
                    post.getViews(),
                    post.getReplyCount(),
                    post.getLikeCount(),
                    status
            });

            // 建立映射关系
            mainForumView.rowToPostMap.put(rowIndex, post);
            rowIndex++;
        }
    }

    /**
     * 搜索帖子
     *
     * @param keyword
     * @param postController
     * @param mainForumView
     */
    public void searchPosts(String keyword, PostController postController, MainForumView mainForumView) {
        if (keyword.isEmpty()) {
            // 清空搜索状态，恢复显示所有帖子
            mainForumView.isSearching = false;
            mainForumView.searchResults.clear();
            mainForumView.postController.loadPostsForSelectedBoard(mainForumView);
            return;
        }

        mainForumView.isSearching = true;
        List<Post> results = postController.searchPosts(keyword);

        if (results != null && !results.isEmpty()) {
            mainForumView.searchResults = results;
            // 为搜索结果设置点赞数
            for (Post post : mainForumView.searchResults) {
                int likeCount = mainForumView.likeController.getLikeCount(post.getId());
                post.setLikeCount(likeCount);
            }

            postController.refreshPostTable(mainForumView);
            mainForumView.paginationLabel.setText("搜索结果: " + mainForumView.searchResults.size() + " 条记录");
        } else {
            mainForumView.tableModel.setRowCount(0);
            mainForumView.rowToPostMap.clear();
            mainForumView.paginationLabel.setText("没有找到包含 \"" + keyword + "\" 的帖子");
        }
    }

    // 更新帖子元信息
    public void updatePostMetaInfo(JDialog dialog, int postId, MainForumView mainForumView) {
        int newLikeCount = mainForumView.likeController.getLikeCount(postId);

        Component[] components = dialog.getContentPane().getComponents();
        for (Component component : components) {
            if (component instanceof JPanel) {
                JPanel panel = (JPanel) component;
                Component[] subComponents = panel.getComponents();
                for (Component subComponent : subComponents) {
                    if (subComponent instanceof JPanel) {
                        JPanel subPanel = (JPanel) subComponent;
                        Component[] metaComponents = subPanel.getComponents();
                        for (Component metaComponent : metaComponents) {
                            if (metaComponent instanceof JLabel) {
                                JLabel label = (JLabel) metaComponent;
                                String text = label.getText();
                                if (text != null && text.contains("点赞数")) {
                                    label.setText(" | 点赞数: " + newLikeCount);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void loadPostsForSelectedBoard(MainForumView mainForumView) {
        if (mainForumView.selectedBoardId == -1) return;

        List<Post> posts = getBoardPosts(mainForumView.selectedBoardId, mainForumView.currentPage, mainForumView.pageSize);
        if (posts != null) {
            // 为每个帖子设置点赞数
            for (Post post : posts) {
                int likeCount = mainForumView.likeController.getLikeCount(post.getId());
                post.setLikeCount(likeCount);
            }
            mainForumView.currentPosts = posts;
            sortAndDisplayPosts(mainForumView);
            mainForumView.updatePaginationInfo();
        }
    }
}