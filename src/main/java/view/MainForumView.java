package view;

import controller.LikeController;
import controller.PostController;
import controller.ReplyController;
import model.Post;
import model.Reply;
import model.User;
import patterns.decorator.AttachmentDecorator;
import patterns.strategy.PostSorter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class MainForumView extends JFrame {
    public User currentUser;
    public PostController postController;
    private ReplyController replyController;
    public LikeController likeController;
    public List<Post> currentPosts;
    public Map<Integer, Post> rowToPostMap = new HashMap<>();  // 新增：行索引到帖子的映射
    public boolean isSearching = false;  // 新增：标记是否处于搜索状态
    public List<Post> searchResults = new ArrayList<>();  // 新增：搜索结果列表
    public int selectedBoardId = -1;
    public int currentPage = 1;
    public int pageSize = 10;
    public PostSorter postSorter;
    public String currentSort = "date";

    public JTable postTable;
    public DefaultTableModel tableModel;
    public JLabel paginationLabel;
    private JTextField searchField;
    private JComboBox<String> boardComboBox;

    public MainForumView(User user) {
        this.currentUser = user;
        this.postController = new PostController();
        this.replyController = new ReplyController();
        this.likeController = new LikeController();
        this.postSorter = new PostSorter();
        currentPosts = new ArrayList<>();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("校园论坛 - 主界面 (" + currentUser.getUsername() + ")");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 750);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // 顶部面板
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(20);
        JButton searchButton = new JButton("搜索");
        searchButton.addActionListener(e -> postController.searchPosts(searchField.getText().trim(), postController, this));

        boardComboBox = new JComboBox<>(new String[]{"全部版块", "技术支持", "学习交流", "休闲娱乐", "校园生活"});
        boardComboBox.addActionListener(e -> {
            int selectedIndex = boardComboBox.getSelectedIndex();
            selectedBoardId = selectedIndex;
            currentPage = 1;
            postController.loadPostsForSelectedBoard(this);
        });

        JButton sortByDateButton = new JButton("按时间排序");
        sortByDateButton.addActionListener(e -> {
            currentSort = "date";
            postController.sortAndDisplayPosts(this);
        });

        JButton sortByViewsButton = new JButton("按浏览量排序");
        sortByViewsButton.addActionListener(e -> {
            currentSort = "views";
            postController.sortAndDisplayPosts(this);
        });

        JButton sortByLikesButton = new JButton("按点赞数排序");
        sortByLikesButton.addActionListener(e -> {
            currentSort = "likes";
            postController.sortAndDisplayPosts(this);
        });

        topPanel.add(new JLabel("版块:"));
        topPanel.add(boardComboBox);
        topPanel.add(new JLabel("搜索:"));
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(sortByDateButton);
        topPanel.add(sortByViewsButton);
        topPanel.add(sortByLikesButton);

        // 帖子表格
        String[] columnNames = {"ID", "标题", "作者", "发布时间", "浏览量", "回复数", "点赞数", "状态"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 7) {
                    return String.class;
                }
                return super.getColumnClass(columnIndex);
            }
        };
        postTable = new JTable(tableModel);

        postTable.getColumnModel().getColumn(0).setMaxWidth(50);
        postTable.getColumnModel().getColumn(3).setMaxWidth(150);
        postTable.getColumnModel().getColumn(4).setMaxWidth(80);
        postTable.getColumnModel().getColumn(5).setMaxWidth(80);
        postTable.getColumnModel().getColumn(6).setMaxWidth(80);
        postTable.getColumnModel().getColumn(7).setMaxWidth(100);

        postTable.setRowHeight(30);

        postTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = postTable.getSelectedRow();
                    if (row >= 0) {
                        Post post = getPostByTableRow(row);
                        if (post != null) {
                            openPostDetail(post);
                        } else {
                            JOptionPane.showMessageDialog(MainForumView.this,
                                    "无法找到对应的帖子，请刷新后重试。", "错误", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        JScrollPane tableScroll = new JScrollPane(postTable);

        // 底部面板
        JPanel bottomPanel = new JPanel(new BorderLayout());
        paginationLabel = new JLabel();
        bottomPanel.add(paginationLabel, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton refreshButton = new JButton("刷新");
        refreshButton.addActionListener(e -> {
            isSearching = false;
            searchField.setText("");
            postController.loadPostsForSelectedBoard(this);
        });

        JButton newPostButton = new JButton("发新帖");
        newPostButton.addActionListener(e -> {
            if (selectedBoardId == -1 || selectedBoardId == 0) {
                JOptionPane.showMessageDialog(this, "请先选择版块！");
                return;
            }
            new PostCreateView(currentUser, selectedBoardId, () -> postController.loadPostsForSelectedBoard(this));
        });

        JButton deleteButton = new JButton("删除帖子");
        deleteButton.addActionListener(e -> postController.deleteSelectedPost(this));

        JButton userInfoButton = new JButton("个人信息");
        userInfoButton.addActionListener(e -> new UserInfoView(currentUser, () -> {
            JOptionPane.showMessageDialog(MainForumView.this, "个人信息已更新");
        }));

        JButton myLikesButton = new JButton("我的点赞");
        myLikesButton.addActionListener(e -> postController.showMyLikedPosts(this));

        buttonPanel.add(refreshButton);
        buttonPanel.add(newPostButton);
        if (canDeletePost()) {
            buttonPanel.add(deleteButton);
        }
        buttonPanel.add(userInfoButton);
        buttonPanel.add(myLikesButton);

        bottomPanel.add(buttonPanel, BorderLayout.EAST);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(tableScroll, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(mainPanel);

        postController.loadPostsForSelectedBoard(this);
    }

    /**
     * 根据表格行索引获取对应的帖子对象
     * 使用映射确保准确性
     */
    public Post getPostByTableRow(int tableRow) {
        return rowToPostMap.get(tableRow);
    }

    /**
     * 打开帖子详情页面
     */
    public void openPostDetail(Post post) {
        // 获取最新帖子信息
        Post latestPost = postController.getPostDetail(post.getId());
        if (latestPost == null) {
            JOptionPane.showMessageDialog(this, "帖子不存在或已被删除", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 获取点赞数
        int likeCount = likeController.getLikeCount(latestPost.getId());
        latestPost.setLikeCount(likeCount);

        JDialog detailDialog = new JDialog(this, "帖子详情 - " + latestPost.getTitle(), true);
        detailDialog.setSize(950, 750);
        detailDialog.setLocationRelativeTo(this);
        detailDialog.setLayout(new BorderLayout());

        // 帖子内容区域
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel(latestPost.getTitle(), JLabel.CENTER);
        titleLabel.setFont(new Font("宋体", Font.BOLD, 20));
        headerPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel metaPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        metaPanel.add(new JLabel("作者: " + latestPost.getUsername()));
        metaPanel.add(new JLabel(" | 发布时间: " + latestPost.getCreatedAt()));
        metaPanel.add(new JLabel(" | 浏览量: " + latestPost.getViews()));
        metaPanel.add(new JLabel(" | 回复数: " + latestPost.getReplyCount()));
        metaPanel.add(new JLabel(" | 点赞数: " + latestPost.getLikeCount()));
        headerPanel.add(metaPanel, BorderLayout.SOUTH);
        contentPanel.add(headerPanel, BorderLayout.NORTH);

        JTextPane contentPane = new JTextPane();
        contentPane.setEditable(false);
        contentPane.setContentType("text/html");
        contentPane.setEditorKit(new HTMLEditorKit());

        // 使用装饰器模式显示附件信息
        Post decoratedPost = latestPost;
        if (latestPost.getAttachment() != null && !latestPost.getAttachment().isEmpty()) {
            decoratedPost = new AttachmentDecorator(latestPost, latestPost.getAttachment());
        }

        String contentHtml = "<html><body style='padding:15px; font-size:14px;'>" + decoratedPost.getContent();
        contentHtml += "</body></html>";
        contentPane.setText(contentHtml);

        JScrollPane contentScroll = new JScrollPane(contentPane);
        contentScroll.setPreferredSize(new Dimension(0, 300));
        contentPanel.add(contentScroll, BorderLayout.CENTER);
        detailDialog.add(contentPanel, BorderLayout.CENTER);

        // 回复区域
        JPanel replyContainer = new JPanel(new BorderLayout());
        replyContainer.setBorder(BorderFactory.createTitledBorder("回复列表 (" + latestPost.getReplyCount() + " 条回复)"));

        String[] replyColumns = {"回复人", "回复内容", "回复时间"};
        DefaultTableModel replyTableModel = new DefaultTableModel(replyColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable replyTable = new JTable(replyTableModel);
        replyTable.getColumnModel().getColumn(0).setMaxWidth(100);
        replyTable.getColumnModel().getColumn(2).setMaxWidth(150);

        JScrollPane replyScroll = new JScrollPane(replyTable);
        replyScroll.setPreferredSize(new Dimension(0, 200));
        replyContainer.add(replyScroll, BorderLayout.CENTER);
        loadRepliesToTable(latestPost.getId(), replyTableModel);

        JPanel replyInputPanel = new JPanel(new BorderLayout(5, 5));
        replyInputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel replyLabel = new JLabel("发表回复:");
        replyInputPanel.add(replyLabel, BorderLayout.NORTH);

        JTextArea replyTextArea = new JTextArea(4, 40);
        replyTextArea.setLineWrap(true);
        replyTextArea.setWrapStyleWord(true);
        replyTextArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane replyInputScroll = new JScrollPane(replyTextArea);

        JPanel replyButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton submitReplyButton = new JButton("发表回复");
        submitReplyButton.addActionListener(e -> submitReply(latestPost, replyTextArea, replyTableModel, detailDialog));

        JButton clearButton = new JButton("清空");
        clearButton.addActionListener(e -> replyTextArea.setText(""));

        replyButtonPanel.add(clearButton);
        replyButtonPanel.add(submitReplyButton);

        replyInputPanel.add(replyInputScroll, BorderLayout.CENTER);
        replyInputPanel.add(replyButtonPanel, BorderLayout.SOUTH);
        replyContainer.add(replyInputPanel, BorderLayout.SOUTH);
        detailDialog.add(replyContainer, BorderLayout.SOUTH);

        // 操作按钮区域
        JPanel actionPanel = new JPanel(new FlowLayout());

        // 点赞按钮
        JButton likeButton = new JButton();
        likeController.likeService.updateLikeButtonText(likeButton, latestPost.getId(), this, likeController);
        likeButton.setFont(new Font("Dialog", Font.PLAIN, 14));
        likeButton.addActionListener(e -> {
            String result = likeController.likeWithVisitor(latestPost, currentUser.getId());
            JOptionPane.showMessageDialog(detailDialog, result);
            likeController.likeService.updateLikeButtonText(likeButton, latestPost.getId(), this, likeController);
            postController.loadPostsForSelectedBoard(this);
            postController.updatePostMetaInfo(detailDialog, latestPost.getId(), this);
        });
        actionPanel.add(likeButton);

        // 置顶/取消置顶按钮
        if (canPinPost()) {
            JButton pinButton;
            boolean isPinned = latestPost.isPinned();
            if (isPinned) {
                pinButton = new JButton("取消置顶");
                pinButton.addActionListener(e -> {
                    boolean success = postController.togglePinPost(latestPost.getId(), false);
                    if (success) {
                        JOptionPane.showMessageDialog(detailDialog, "已取消置顶！");
                        detailDialog.dispose();
                        postController.loadPostsForSelectedBoard(this);
                    }
                });
            } else {
                pinButton = new JButton("置顶帖子");
                pinButton.addActionListener(e -> {
                    boolean success = postController.togglePinPost(latestPost.getId(), true);
                    if (success) {
                        JOptionPane.showMessageDialog(detailDialog, "帖子已置顶！");
                        detailDialog.dispose();
                        postController.loadPostsForSelectedBoard(this);
                    }
                });
            }
            actionPanel.add(pinButton);
        }

        // 附件打开按钮
        if (latestPost.getAttachment() != null && !latestPost.getAttachment().isEmpty()) {
            JButton openAttachmentButton = new JButton("打开附件");
            openAttachmentButton.addActionListener(e -> {
                try {
                    Desktop.getDesktop().open(new File(latestPost.getAttachment()));
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(detailDialog, "打开附件失败：" + ex.getMessage());
                }
            });
            actionPanel.add(openAttachmentButton);
        }

        // 编辑按钮
        if (canEditPost(latestPost)) {
            JButton editButton = new JButton("编辑帖子");
            editButton.addActionListener(e -> {
                detailDialog.dispose();
                new EditPostView(currentUser, latestPost, () -> postController.loadPostsForSelectedBoard(this));
            });
            actionPanel.add(editButton);
        }

        JButton closeButton = new JButton("关闭");
        closeButton.addActionListener(e -> detailDialog.dispose());
        actionPanel.add(closeButton);

        detailDialog.add(actionPanel, BorderLayout.NORTH);
        detailDialog.setVisible(true);
        postController.loadPostsForSelectedBoard(this);
    }

    private boolean canPinPost() {
        String role = currentUser.getRole();
        return "admin".equals(role) || "moderator".equals(role);
    }

    private void submitReply(Post post, JTextArea replyTextArea,
                             DefaultTableModel replyTableModel, JDialog dialog) {
        String content = replyTextArea.getText().trim();
        if (content.isEmpty()) {
            JOptionPane.showMessageDialog(dialog, "回复内容不能为空！");
            return;
        }

        Reply reply = new Reply();
        reply.setContent(content);
        reply.setPostId(post.getId());
        reply.setUserId(currentUser.getId());

        boolean success = replyController.addReply(reply);
        if (success) {
            JOptionPane.showMessageDialog(dialog, "回复发表成功！");
            replyTextArea.setText("");
            loadRepliesToTable(post.getId(), replyTableModel);
            postController.loadPostsForSelectedBoard(this);
        }
    }

    private void loadRepliesToTable(int postId, DefaultTableModel replyTableModel) {
        replyTableModel.setRowCount(0);
        List<Reply> replies = replyController.getPostReplies(postId);
        if (replies != null) {
            for (Reply reply : replies) {
                String timeStr = reply.getCreatedAt() != null ?
                        reply.getCreatedAt().toString().substring(0, 16) : "未知时间";
                replyTableModel.addRow(new Object[]{
                        reply.getUsername(),
                        reply.getContent(),
                        timeStr
                });
            }
        }
    }

    private void deleteSelectedReply(JTable replyTable, DefaultTableModel replyTableModel) {
        int row = replyTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "请先选择要删除的回复！");
            return;
        }

        String replyUser = (String) replyTableModel.getValueAt(row, 0);
        String replyContent = (String) replyTableModel.getValueAt(row, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
                "确认删除 " + replyUser + " 的回复？\n内容: " + replyContent,
                "删除确认", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this,
                    "删除回复功能需要更复杂的实现，当前版本暂不支持直接删除回复。");
        }
    }

    private boolean canDeletePost() {
        String role = currentUser.getRole();
        return "admin".equals(role) || "moderator".equals(role);
    }

    private boolean canEditPost(Post post) {
        String role = currentUser.getRole();
        if ("admin".equals(role)) return true;
        if ("moderator".equals(role)) return true;
        return currentUser.getId() == post.getUserId();
    }

    public void updatePaginationInfo() {
        int totalPosts = isSearching ? searchResults.size() : currentPosts.size();
        paginationLabel.setText("第 " + currentPage + " 页 - 共 " + totalPosts + " 条帖子");
    }

}